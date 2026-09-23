package com.streakly.app.worker

import android.content.Context
import androidx.work.*
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.session.SessionManager
import com.streakly.app.utils.NetworkMonitor
import java.util.concurrent.TimeUnit

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (!NetworkMonitor.isOnline(applicationContext)) return Result.retry()
        val habitRepo = HabitRepository(applicationContext)
        val session = SessionManager(applicationContext)

        val pushed = habitRepo.pushPending()
        val pulled = habitRepo.refreshFromServer()
        return if (pushed && pulled) {
            session.lastSyncTime = System.currentTimeMillis()
            Result.success()
        } else Result.retry()
    }

    companion object {
        private const val NAME = "streakly_sync"
        fun schedulePeriodic(context: Context) {
            val request = PeriodicWorkRequestBuilder<SyncWorker>(30, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                NAME, ExistingPeriodicWorkPolicy.KEEP, request
            )
        }

        fun runNow(context: Context) {
            val request = OneTimeWorkRequestBuilder<SyncWorker>().build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}
