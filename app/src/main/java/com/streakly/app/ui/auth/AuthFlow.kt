package com.streakly.app.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.Graph
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.push.PushRegistrar
import com.streakly.app.ui.main.MainActivity
import com.streakly.app.worker.ReminderScheduler
import com.streakly.app.worker.SyncScheduler
import kotlinx.coroutines.launch

/** Shared steps that run after every successful sign-in (email or Google). */
object AuthFlow {

    fun homeIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

    fun verifyIntent(context: Context): Intent =
        Intent(context, EmailVerificationActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

    /** Seeds default rewards, starts background sync, registers for push and schedules reminders. */
    suspend fun onSignedIn(context: Context) {
        Graph.rewards.ensureDefaults()
        SyncScheduler.requestNow(context)
        SyncScheduler.applyPeriodic(context)
        PushRegistrar.enqueue(context)
        ReminderScheduler.rescheduleAll(context)
    }

    /** Runs the Google account chooser and, on success, continues to the main screen. */
    fun googleSignIn(activity: AppCompatActivity, onBusy: (Boolean) -> Unit, onError: (Int) -> Unit) {
        activity.lifecycleScope.launch {
            onBusy(true)
            when (val result = Graph.auth.signInWithGoogle(activity)) {
                is AuthResult.Success -> {
                    onSignedIn(activity.applicationContext)
                    activity.startActivity(homeIntent(activity))
                    activity.finish()
                }
                is AuthResult.Cancelled -> onBusy(false)
                is AuthResult.Error -> {
                    onBusy(false)
                    onError(result.messageRes)
                }
            }
        }
    }
}
