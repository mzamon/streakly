package com.streakly.app.data.repository

import android.content.Context
import com.streakly.app.data.local.StreaklyDatabase
import com.streakly.app.data.local.entity.UserEntity
import com.streakly.app.data.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class AuthRepository(private val context: Context) {

    private val db = StreaklyDatabase.get(context)
    private val session = SessionManager(context)

    suspend fun register(name: String, email: String, password: String): AuthResult =
        withContext(Dispatchers.IO) {
            val cleanEmail = email.trim().lowercase()
            if (cleanEmail.isEmpty()) return@withContext AuthResult.Error("Enter your email")
            if (name.trim().isEmpty()) return@withContext AuthResult.Error("Enter your name")
            if (password.length < 6) return@withContext AuthResult.Error("Password must be at least 6 characters")

            if (db.userDao().countByEmail(cleanEmail) > 0) {
                return@withContext AuthResult.Error("That email is already registered")
            }

            val salt = generateSalt()
            val hash = hashPassword(password, salt)
            val user = UserEntity(
                email = cleanEmail,
                name = name.trim(),
                salt = salt,
                passwordHash = hash
            )
            db.userDao().insert(user)

            session.userEmail = cleanEmail
            session.userName = name.trim()
            session.isLoggedIn = true
            AuthResult.Success
        }

    suspend fun login(email: String, password: String): AuthResult =
        withContext(Dispatchers.IO) {
            val cleanEmail = email.trim().lowercase()
            if (cleanEmail.isEmpty()) return@withContext AuthResult.Error("Enter your email")
            if (password.isEmpty()) return@withContext AuthResult.Error("Enter your password")

            val user = db.userDao().findByEmail(cleanEmail)
                ?: return@withContext AuthResult.Error("Check your password and try again")

            val candidate = hashPassword(password, user.salt)
            if (candidate != user.passwordHash) {
                return@withContext AuthResult.Error("Check your password and try again")
            }

            session.userEmail = user.email
            session.userName = user.name
            session.isLoggedIn = true
            AuthResult.Success
        }

    suspend fun changePassword(current: String, next: String): AuthResult =
        withContext(Dispatchers.IO) {
            if (next.length < 6) return@withContext AuthResult.Error("New password must be at least 6 characters")
            val email = session.userEmail ?: return@withContext AuthResult.Error("Not signed in")
            val user = db.userDao().findByEmail(email)
                ?: return@withContext AuthResult.Error("Account not found")
            if (hashPassword(current, user.salt) != user.passwordHash) {
                return@withContext AuthResult.Error("Current password is incorrect")
            }
            val newSalt = generateSalt()
            val newHash = hashPassword(next, newSalt)
            db.userDao().updatePassword(email, newSalt, newHash)
            AuthResult.Success
        }

    suspend fun updateName(name: String): AuthResult = withContext(Dispatchers.IO) {
        val email = session.userEmail ?: return@withContext AuthResult.Error("Not signed in")
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return@withContext AuthResult.Error("Name cannot be blank")
        db.userDao().updateName(email, trimmed)
        session.userName = trimmed
        AuthResult.Success
    }

    suspend fun forgotPassword(email: String) {
        // Demo: show success state to prevent account enumeration.
    }

    suspend fun deleteAccount(): AuthResult = withContext(Dispatchers.IO) {
        val email = session.userEmail ?: return@withContext AuthResult.Error("Not signed in")
        db.userDao().deleteByEmail(email)
        session.clear()
        AuthResult.Success
    }

    fun logout() {
        session.clear()
    }

    private fun generateSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return Base64.getEncoder().encodeToString(bytes)
    }

    private fun hashPassword(password: String, salt: String): String {
        val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), 65_536, 256)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded
        return Base64.getEncoder().encodeToString(hash)
    }
}
