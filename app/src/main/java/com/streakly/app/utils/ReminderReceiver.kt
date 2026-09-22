package com.streakly.app.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.streakly.app.R
import com.streakly.app.StreaklyApplication
import com.streakly.app.ui.main.MainActivity

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val habitId = intent.getLongExtra("habitId", -1L)
        if (habitId < 0) return
        val pi = PendingIntent.getActivity(
            context, habitId.toInt(), Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        val n = NotificationCompat.Builder(context, StreaklyApplication.CHANNEL_REMINDERS)
            .setSmallIcon(R.drawable.ic_reminder)
            .setContentTitle("Time for your habit")
            .setContentText("Tap to log it in Streakly")
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()
        val nm = context.getSystemService(NotificationManager::class.java)
        nm.notify(habitId.toInt() + 5000, n)
    }
}
