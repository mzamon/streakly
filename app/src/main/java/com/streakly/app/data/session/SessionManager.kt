package com.streakly.app.data.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("streakly_prefs", Context.MODE_PRIVATE)

    var authToken: String?
        get() = prefs.getString("auth_token", null)
        set(value) = prefs.edit().putString("auth_token", value).apply()

    var userName: String?
        get() = prefs.getString("user_name", "User")
        set(value) = prefs.edit().putString("user_name", value).apply()

    var userEmail: String?
        get() = prefs.getString("user_email", "")
        set(value) = prefs.edit().putString("user_email", value).apply()

    var theme: String
        get() = prefs.getString("theme", "system") ?: "system"
        set(value) = prefs.edit().putString("theme", value).apply()

    var language: String
        get() = prefs.getString("language", "system") ?: "system"
        set(value) = prefs.edit().putString("language", value).apply()

    var notificationsEnabled: Boolean
        get() = prefs.getBoolean("notifications_enabled", true)
        set(value) = prefs.edit().putBoolean("notifications_enabled", value).apply()

    var lastSyncTime: Long
        get() = prefs.getLong("last_sync_time", 0L)
        set(value) = prefs.edit().putLong("last_sync_time", value).apply()

    var lastRiskAlertDate: String?
        get() = prefs.getString("last_risk_alert_date", null)
        set(value) = prefs.edit().putString("last_risk_alert_date", value).apply()

    fun clear() {
        prefs.edit().clear().apply()
    }
}