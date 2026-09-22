package com.streakly.app.data.session

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SessionManager(context: Context) {

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "streakly_secure_prefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var userEmail: String?
        get() = prefs.getString(KEY_EMAIL, null)
        set(v) { prefs.edit().putString(KEY_EMAIL, v).apply() }

    var userName: String?
        get() = prefs.getString(KEY_NAME, null)
        set(v) { prefs.edit().putString(KEY_NAME, v).apply() }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_LOGGED, false)
        set(v) { prefs.edit().putBoolean(KEY_LOGGED, v).apply() }

    var notificationsEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIF, true)
        set(v) { prefs.edit().putBoolean(KEY_NOTIF, v).apply() }

    var theme: String
        get() = prefs.getString(KEY_THEME, "dark") ?: "dark"
        set(v) { prefs.edit().putString(KEY_THEME, v).apply() }

    var language: String
        get() = prefs.getString(KEY_LANG, "system") ?: "system"
        set(v) { prefs.edit().putString(KEY_LANG, v).apply() }

    var lastSyncTime: Long
        get() = prefs.getLong(KEY_LAST_SYNC, 0L)
        set(v) { prefs.edit().putLong(KEY_LAST_SYNC, v).apply() }

    var lastRiskAlertDate: String?
        get() = prefs.getString(KEY_RISK_DATE, null)
        set(v) { prefs.edit().putString(KEY_RISK_DATE, v).apply() }

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "name"
        private const val KEY_LOGGED = "logged"
        private const val KEY_NOTIF = "notif"
        private const val KEY_THEME = "theme"
        private const val KEY_LANG = "lang"
        private const val KEY_LAST_SYNC = "last_sync"
        private const val KEY_RISK_DATE = "risk_date"
    }
}