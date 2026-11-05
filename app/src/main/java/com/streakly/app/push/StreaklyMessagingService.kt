package com.streakly.app.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.streakly.app.Graph
import com.streakly.app.R

/**
 * Receives server-triggered push messages. The server sends data-only messages so this callback runs in every
 * app state (foreground, background, killed) and the text is translated on the device.
 */
class StreaklyMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM token refreshed")
        PushRegistrar.enqueue(applicationContext)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        val type = data["type"] ?: return
        Log.d(TAG, "Push received: type=$type")
        val settings = Graph.settings
        if (!settings.pushEnabled) return
        if (!Graph.auth.isSignedIn) return

        when (type) {
            "STREAK_RISK" -> {
                if (!settings.streakRiskAlerts) return
                val name = data["habitName"] ?: getString(R.string.app_name)
                val streak = data["streak"]?.toIntOrNull() ?: 0
                NotificationHelper.show(
                    this, NotificationHelper.CH_STREAK, 2001,
                    getString(R.string.push_streak_risk_title),
                    resources.getQuantityString(R.plurals.push_streak_risk_body, streak, name, streak),
                    AppIntents.DEST_HABIT, data["habitId"]
                )
            }
            "REWARD" -> NotificationHelper.show(
                this, NotificationHelper.CH_REWARDS, 2002,
                getString(R.string.push_reward_title), getString(R.string.push_reward_body),
                AppIntents.DEST_REWARDS
            )
            "SYNC_CONFLICT" -> NotificationHelper.show(
                this, NotificationHelper.CH_SYNC, 2003,
                getString(R.string.push_conflict_title), getString(R.string.push_conflict_body),
                AppIntents.DEST_SYNC
            )
            "TEST" -> NotificationHelper.show(
                this, NotificationHelper.CH_STREAK, 2004,
                getString(R.string.push_test_title), getString(R.string.push_test_body),
                AppIntents.DEST_HOME
            )
        }
    }

    companion object {
        private const val TAG = "StreaklyFcm"
    }
}
