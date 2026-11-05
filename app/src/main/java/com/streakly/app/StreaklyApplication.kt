package com.streakly.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.streakly.app.push.NotificationHelper
import com.streakly.app.worker.SyncScheduler

class StreaklyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.init(this)
        applyTheme(Graph.settings.theme)
        NotificationHelper.createChannels(this)
        if (Graph.auth.isSignedIn) SyncScheduler.applyPeriodic(this)
    }

    companion object {
        fun applyTheme(theme: String) {
            AppCompatDelegate.setDefaultNightMode(
                when (theme) {
                    "light" -> AppCompatDelegate.MODE_NIGHT_NO
                    "system" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> AppCompatDelegate.MODE_NIGHT_YES
                }
            )
        }

        /** "en" / "zu" / "tn", or "system" to follow the phone. AppCompat stores the choice and recreates screens. */
        fun applyLanguage(tag: String) {
            val locales = if (tag == "system") LocaleListCompat.getEmptyLocaleList() else LocaleListCompat.forLanguageTags(tag)
            AppCompatDelegate.setApplicationLocales(locales)
        }
    }
}
