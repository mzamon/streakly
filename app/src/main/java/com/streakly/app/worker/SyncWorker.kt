package com.streakly.app.worker

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.streakly.app.Graph
import com.streakly.app.data.repository.SyncOutcome
import java.util.concurrent.TimeUnit

/** Background synchronisation: runs as soon as a network connection is available. */
class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d(TAG, "Sync worker started, attempt=$runAttemptCount")
        return when (val outcome = Graph.sync.sync()) {
            is SyncOutcome.Success -> {
                ReminderScheduler.rescheduleAll(applicationContext)
                Result.success()
            }
            SyncOutcome.NotSignedIn -> Result.success()
            SyncOutcome.Offline -> Result.retry()
            is SyncOutcome.Failed -> {
                Log.w(TAG, "Sync failed: ${outcome.message}")
                if (runAttemptCount < 5) Result.retry() else Result.failure()
            }
        }
    }

    companion object {
        private const val TAG = "SyncWorker"
    }
}

object SyncScheduler {
    private val connected = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    /** Queue a sync; WorkManager waits for connectivity, so offline edits upload automatically on reconnect. */
    fun requestNow(context: Context) {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(connected)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context.applicationContext)
            .enqueueUniqueWork("sync_now", ExistingWorkPolicy.APPEND_OR_REPLACE, request)
    }

    fun applyPeriodic(context: Context) {
        val wm = WorkManager.getInstance(context.applicationContext)
        if (!Graph.settings.autoSync) {
            wm.cancelUniqueWork("sync_periodic")
            return
        }
        val minutes = Graph.settings.syncMinutes.coerceAtLeast(15).toLong()
        val request = PeriodicWorkRequestBuilder<SyncWorker>(minutes, TimeUnit.MINUTES)
            .setConstraints(connected)
            .build()
        wm.enqueueUniquePeriodicWork("sync_periodic", ExistingPeriodicWorkPolicy.UPDATE, request)
    }
}
