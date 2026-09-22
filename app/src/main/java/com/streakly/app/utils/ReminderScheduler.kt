package com.streakly.app.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.streakly.app.data.model.Habit
import java.util.Calendar

object ReminderScheduler {

    fun schedule(context: Context, habitId: Long, hour: Int, minute: Int) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("habitId", habitId)
        }
        val pi = PendingIntent.getBroadcast(
            context, habitId.toInt(), intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour); set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DAY_OF_MONTH, 1)
        }
        am.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, cal.timeInMillis,
            AlarmManager.INTERVAL_DAY, pi
        )
    }

    fun cancel(context: Context, habitId: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pi = PendingIntent.getBroadcast(
            context, habitId.toInt(), intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        )
        if (pi != null) am.cancel(pi)
    }

    fun rescheduleAll(context: Context, habits: List<Habit>, enabled: Boolean) {
        for (h in habits) {
            if (enabled && h.reminderHour >= 0) schedule(context, h.localId, h.reminderHour, h.reminderMinute)
            else cancel(context, h.localId)
        }
    }
}
