package com.streakly.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.streakly.app.data.session.SessionManager

class StreaklyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createChannels()
        val session = SessionManager(this)
        
        // Apply theme settings
        when (session.theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        // Apply locale settings
        if (session.language != "system") {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(session.language))
        }
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(NotificationChannel(CHANNEL_REMINDERS, "Habit reminders", NotificationManager.IMPORTANCE_DEFAULT))
            nm.createNotificationChannel(NotificationChannel(CHANNEL_RISK, "Streak risk alerts", NotificationManager.IMPORTANCE_HIGH))
        }
    }

    companion object {
        const val CHANNEL_REMINDERS = "reminders"
        const val CHANNEL_RISK = "risk"
    }
}