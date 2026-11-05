package com.streakly.app.push

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.CoroutineWorker
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.firebase.messaging.FirebaseMessaging
import com.streakly.app.Graph
import com.streakly.app.data.FirebaseConfig
import com.streakly.app.data.remote.RetrofitClient
import com.streakly.app.data.remote.dto.DeviceRequest
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/** Sends this device's FCM token (and language / time zone) to the API so the server can target it. */
object PushRegistrar {
    fun enqueue(context: Context) {
        val request = OneTimeWorkRequestBuilder<PushTokenWorker>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork("push_token", ExistingWorkPolicy.REPLACE, request)
    }
}

class PushTokenWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (!FirebaseConfig.isReady() || !Graph.auth.isSignedIn) return Result.success()
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            val language = Locale.getDefault().language.ifBlank { "en" }
            RetrofitClient.api.registerDevice(DeviceRequest(token, language, TimeZone.getDefault().id))
            Log.d("PushTokenWorker", "Device registered")
            Result.success()
        } catch (e: IOException) {
            Result.retry()
        } catch (e: Exception) {
            Log.w("PushTokenWorker", "Registration failed: ${e.message}")
            if (runAttemptCount < 4) Result.retry() else Result.failure()
        }
    }
}
