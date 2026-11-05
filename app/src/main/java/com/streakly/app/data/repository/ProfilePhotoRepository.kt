package com.streakly.app.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.FirebaseConfig
import com.streakly.app.utils.ImageUtils
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

/** Profile photo in Firebase Storage (blob storage): users/{uid}/profile.jpg */
object ProfilePhotoRepository {
    private const val TAG = "ProfilePhoto"

    suspend fun upload(context: Context, source: Uri): AuthResult {
        val uid = Graph.auth.uid ?: return AuthResult.Error(R.string.error_generic)
        if (!FirebaseConfig.isReady()) return AuthResult.Error(R.string.error_firebase_not_configured)
        val bytes = ImageUtils.compressToJpeg(context, source) ?: return AuthResult.Error(R.string.error_image)
        return try {
            val ref = FirebaseStorage.getInstance().reference.child("users/$uid/profile.jpg")
            ref.putBytes(bytes).await()
            val url = ref.downloadUrl.await()
            Graph.auth.updatePhoto(url)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "Upload failed: ${e.message}")
            AuthResult.Error(R.string.error_photo_upload)
        }
    }

    suspend fun remove(): AuthResult {
        val uid = Graph.auth.uid ?: return AuthResult.Error(R.string.error_generic)
        try {
            if (FirebaseConfig.isReady()) FirebaseStorage.getInstance().reference.child("users/$uid/profile.jpg").delete().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "Storage delete skipped: ${e.message}")
        }
        return Graph.auth.updatePhoto(null)
    }
}
