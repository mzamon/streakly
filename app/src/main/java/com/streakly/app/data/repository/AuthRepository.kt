package com.streakly.app.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.messaging.FirebaseMessaging
import com.streakly.app.R
import com.streakly.app.data.FirebaseConfig
import com.streakly.app.data.remote.RetrofitClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull

sealed class AuthResult {
    object Success : AuthResult()
    object Cancelled : AuthResult()
    data class Error(@StringRes val messageRes: Int) : AuthResult()
}

class GoogleNotConfiguredException : Exception("Google sign-in is not configured (add google-services.json)")

/**
 * Authentication with Firebase. Firebase Authentication hashes and stores the passwords (they never touch
 * our own API or database) and issues the ID tokens that the REST API verifies.
 */
class AuthRepository(private val context: Context) {

    private fun auth(): FirebaseAuth? = if (FirebaseConfig.isReady()) FirebaseAuth.getInstance() else null

    val isConfigured: Boolean get() = FirebaseConfig.isReady()
    val currentUser: FirebaseUser? get() = auth()?.currentUser
    val uid: String? get() = currentUser?.uid
    val isSignedIn: Boolean get() = currentUser != null

    private fun providers(): List<String> =
        currentUser?.providerData?.map { it.providerId }?.filter { it != "firebase" } ?: emptyList()

    val hasPassword: Boolean get() = providers().contains("password")
    val hasGoogle: Boolean get() = providers().contains("google.com")
    val providerCount: Int get() = providers().size

    private suspend fun guarded(block: suspend () -> AuthResult): AuthResult =
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            map(e)
        }

    private fun map(e: Exception): AuthResult {
        Log.w(TAG, "Auth error: ${e.javaClass.simpleName}: ${e.message}")
        return when (e) {
            is GetCredentialCancellationException -> AuthResult.Cancelled
            is FirebaseAuthWeakPasswordException -> AuthResult.Error(R.string.error_weak_password)
            is FirebaseAuthInvalidCredentialsException -> AuthResult.Error(R.string.error_invalid_credentials)
            is FirebaseAuthInvalidUserException -> AuthResult.Error(R.string.error_account_not_found)
            is FirebaseAuthUserCollisionException -> AuthResult.Error(R.string.error_email_exists)
            is FirebaseAuthRecentLoginRequiredException -> AuthResult.Error(R.string.error_recent_login)
            is FirebaseNetworkException -> AuthResult.Error(R.string.error_network)
            is FirebaseTooManyRequestsException -> AuthResult.Error(R.string.error_too_many)
            is NoCredentialException -> AuthResult.Error(R.string.error_no_google_account)
            is GoogleNotConfiguredException -> AuthResult.Error(R.string.error_google_not_configured)
            else -> AuthResult.Error(R.string.error_generic)
        }
    }

    private val notConfigured = AuthResult.Error(R.string.error_firebase_not_configured)

    suspend fun register(name: String, email: String, password: String): AuthResult = guarded {
        val a = auth() ?: return@guarded notConfigured
        val result = a.createUserWithEmailAndPassword(email.trim(), password).await()
        val user = result.user
        if (user != null) {
            user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name.trim()).build()).await()
            try {
                user.sendEmailVerification().await()
            } catch (e: Exception) {
                Log.w(TAG, "Verification email could not be sent: ${e.message}")
            }
        }
        AuthResult.Success
    }

    suspend fun login(email: String, password: String): AuthResult = guarded {
        val a = auth() ?: return@guarded notConfigured
        a.signInWithEmailAndPassword(email.trim(), password).await()
        AuthResult.Success
    }

    suspend fun sendPasswordReset(email: String): AuthResult = guarded {
        val a = auth() ?: return@guarded notConfigured
        a.sendPasswordResetEmail(email.trim()).await()
        AuthResult.Success
    }

    suspend fun resendVerification(): AuthResult = guarded {
        val user = currentUser ?: return@guarded AuthResult.Error(R.string.error_generic)
        user.sendEmailVerification().await()
        AuthResult.Success
    }

    /** Reloads the user from Firebase and returns whether the email address is verified. */
    suspend fun refreshVerified(): Boolean = try {
        val user = currentUser
        user?.reload()?.await()
        currentUser?.isEmailVerified == true
    } catch (e: Exception) {
        Log.w(TAG, "Reload failed: ${e.message}")
        false
    }

    private suspend fun googleCredential(activity: Activity): AuthCredential {
        val clientId = FirebaseConfig.webClientId() ?: throw GoogleNotConfiguredException()
        val option = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(clientId)
            .setAutoSelectEnabled(false)
            .build()
        val request = GetCredentialRequest.Builder().addCredentialOption(option).build()
        val response = CredentialManager.create(activity).getCredential(activity, request)
        val credential = response.credential
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleId = GoogleIdTokenCredential.createFrom(credential.data)
            return GoogleAuthProvider.getCredential(googleId.idToken, null)
        }
        throw IllegalStateException("Unexpected credential type")
    }

    suspend fun signInWithGoogle(activity: Activity): AuthResult = guarded {
        val a = auth() ?: return@guarded notConfigured
        val credential = googleCredential(activity)
        a.signInWithCredential(credential).await()
        AuthResult.Success
    }

    suspend fun linkGoogle(activity: Activity): AuthResult = guarded {
        val user = currentUser ?: return@guarded AuthResult.Error(R.string.error_generic)
        val credential = googleCredential(activity)
        try {
            user.linkWithCredential(credential).await()
            AuthResult.Success
        } catch (e: FirebaseAuthUserCollisionException) {
            AuthResult.Error(R.string.error_google_in_use)
        }
    }

    suspend fun unlinkGoogle(): AuthResult = guarded {
        val user = currentUser ?: return@guarded AuthResult.Error(R.string.error_generic)
        if (providerCount <= 1) return@guarded AuthResult.Error(R.string.error_cannot_unlink)
        user.unlink("google.com").await()
        AuthResult.Success
    }

    suspend fun changePassword(current: String, newPassword: String): AuthResult = guarded {
        val user = currentUser ?: return@guarded AuthResult.Error(R.string.error_generic)
        val email = user.email ?: return@guarded AuthResult.Error(R.string.error_generic)
        user.reauthenticate(EmailAuthProvider.getCredential(email, current)).await()
        user.updatePassword(newPassword).await()
        AuthResult.Success
    }

    suspend fun updateDisplayName(name: String): AuthResult = guarded {
        val user = currentUser ?: return@guarded AuthResult.Error(R.string.error_generic)
        user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name.trim()).build()).await()
        AuthResult.Success
    }

    suspend fun updatePhoto(url: android.net.Uri?): AuthResult = guarded {
        val user = currentUser ?: return@guarded AuthResult.Error(R.string.error_generic)
        user.updateProfile(UserProfileChangeRequest.Builder().setPhotoUri(url).build()).await()
        user.reload().await()
        AuthResult.Success
    }

    /**
     * Deletes the cloud data (through the API), then the Firebase account. The user is re-authenticated first
     * because Firebase only allows this for a recent sign-in.
     */
    suspend fun deleteAccount(password: String?, activity: Activity): AuthResult = guarded {
        val user = currentUser ?: return@guarded AuthResult.Error(R.string.error_generic)
        val credential: AuthCredential = if (hasPassword && !password.isNullOrEmpty()) {
            EmailAuthProvider.getCredential(user.email ?: "", password)
        } else if (hasGoogle) {
            googleCredential(activity)
        } else {
            return@guarded AuthResult.Error(R.string.error_invalid_credentials)
        }
        user.reauthenticate(credential).await()
        try {
            RetrofitClient.api.deleteAccount()
        } catch (e: Exception) {
            Log.w(TAG, "Server data deletion failed: ${e.message}")
            return@guarded AuthResult.Error(R.string.error_delete_server)
        }
        user.delete().await()
        AuthResult.Success
    }

    /** Signs out, unregisters this device from push and clears cached credentials. Never throws. */
    suspend fun signOut() {
        withTimeoutOrNull(4000) {
            try {
                if (currentUser != null) {
                    val token = FirebaseMessaging.getInstance().token.await()
                    RetrofitClient.api.unregisterDevice(
                        com.streakly.app.data.remote.dto.DeviceRequest(token = token)
                    )
                }
            } catch (e: Exception) {
                Log.w(TAG, "Device unregister skipped: ${e.message}")
            }
        }
        try {
            if (FirebaseConfig.isReady()) FirebaseMessaging.getInstance().deleteToken().await()
        } catch (e: Exception) {
            Log.w(TAG, "Token delete skipped: ${e.message}")
        }
        auth()?.signOut()
        try {
            CredentialManager.create(context).clearCredentialState(ClearCredentialStateRequest())
        } catch (e: Exception) {
            Log.w(TAG, "Credential state not cleared: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}
