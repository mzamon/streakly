package com.streakly.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.streakly.app.data.session.SessionManager

class StreaklyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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
}