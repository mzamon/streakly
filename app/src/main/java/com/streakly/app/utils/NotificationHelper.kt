package com.streakly.app.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.streakly.app.R
import com.streakly.app.StreaklyApplication
import com.streakly.app.ui.main.MainActivity

object NotificationHelper {

    fun showStreakRisk(context: Context, habitName: String) {
        val intent = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val n = NotificationCompat.Builder(context, StreaklyApplication.CHANNEL_RISK)
            .setSmallIcon(R.drawable.ic_streak)
            .setContentTitle(context.getString(R.string.streak_risk_title))
            .setContentText("$habitName — ${context.getString(R.string.streak_risk_body)}")
            .setAutoCancel(true)
            .setContentIntent(pi)
            .build()
        val nm = context.getSystemService(NotificationManager::class.java)
        nm.notify(NOTIF_RISK, n)
    }

    private const val NOTIF_RISK = 2001
}
