package com.streakly.app.data.session

import android.content.Context
import android.content.SharedPreferences

/**
 * User settings (theme, language, notification and sync options). Credentials are never stored here:
 * Firebase Authentication hashes and stores passwords and keeps the session token.
 */
class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("streakly_settings", Context.MODE_PRIVATE)

    var onboardingDone: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING, false)
        set(v) { prefs.edit().putBoolean(KEY_ONBOARDING, v).apply() }

    /** "dark" (default), "light" or "system". */
    var theme: String
        get() = prefs.getString(KEY_THEME, "dark") ?: "dark"
        set(v) { prefs.edit().putString(KEY_THEME, v).apply() }

    /** BCP-47 tag ("en", "zu", "tn") or "system". */
    var language: String
        get() = prefs.getString(KEY_LANG, "system") ?: "system"
        set(v) { prefs.edit().putString(KEY_LANG, v).apply() }

    var pushEnabled: Boolean
        get() = prefs.getBoolean(KEY_PUSH, true)
        set(v) { prefs.edit().putBoolean(KEY_PUSH, v).apply() }

    var streakRiskAlerts: Boolean
        get() = prefs.getBoolean(KEY_RISK, true)
        set(v) { prefs.edit().putBoolean(KEY_RISK, v).apply() }

    var habitRemindersEnabled: Boolean
        get() = prefs.getBoolean(KEY_HABIT_REMINDERS, true)
        set(v) { prefs.edit().putBoolean(KEY_HABIT_REMINDERS, v).apply() }

    var dailyReminderEnabled: Boolean
        get() = prefs.getBoolean(KEY_DAILY, false)
        set(v) { prefs.edit().putBoolean(KEY_DAILY, v).apply() }

    var dailyReminderHour: Int
        get() = prefs.getInt(KEY_DAILY_H, 20)
        set(v) { prefs.edit().putInt(KEY_DAILY_H, v).apply() }

    var dailyReminderMinute: Int
        get() = prefs.getInt(KEY_DAILY_M, 0)
        set(v) { prefs.edit().putInt(KEY_DAILY_M, v).apply() }

    /** "MONDAY" or "SUNDAY". */
    var weekStart: String
        get() = prefs.getString(KEY_WEEK_START, "MONDAY") ?: "MONDAY"
        set(v) { prefs.edit().putString(KEY_WEEK_START, v).apply() }

    /** "system", "12" or "24". */
    var timeFormat: String
        get() = prefs.getString(KEY_TIME_FORMAT, "system") ?: "system"
        set(v) { prefs.edit().putString(KEY_TIME_FORMAT, v).apply() }

    var autoSync: Boolean
        get() = prefs.getBoolean(KEY_AUTO_SYNC, true)
        set(v) { prefs.edit().putBoolean(KEY_AUTO_SYNC, v).apply() }

    /** Minutes between background syncs: 15, 30, 60 or 360. */
    var syncMinutes: Int
        get() = prefs.getInt(KEY_SYNC_MIN, 30)
        set(v) { prefs.edit().putInt(KEY_SYNC_MIN, v).apply() }

    var notificationPromptShown: Boolean
        get() = prefs.getBoolean(KEY_NOTIF_PROMPT, false)
        set(v) { prefs.edit().putBoolean(KEY_NOTIF_PROMPT, v).apply() }

    /** Local-only profile extras (phone, date of birth) kept per account. */
    fun profileField(uid: String, key: String): String = prefs.getString("profile_${uid}_$key", "") ?: ""
    fun setProfileField(uid: String, key: String, value: String) {
        prefs.edit().putString("profile_${uid}_$key", value).apply()
    }

    var lastRiskAlertDate: String?
        get() = prefs.getString(KEY_RISK_DATE, null)
        set(v) { prefs.edit().putString(KEY_RISK_DATE, v).apply() }

    /** Server version (cursor) of the last pull for this user. */
    fun sinceFor(uid: String): Long = prefs.getLong("since_$uid", 0L)
    fun setSince(uid: String, value: Long) { prefs.edit().putLong("since_$uid", value).apply() }

    /** Wall-clock time of the last successful sync for this user (0 = never). */
    fun lastSyncAt(uid: String): Long = prefs.getLong("synced_$uid", 0L)
    fun setLastSyncAt(uid: String, value: Long) { prefs.edit().putLong("synced_$uid", value).apply() }

    fun clearUser(uid: String) {
        prefs.edit().remove("since_$uid").remove("synced_$uid").apply()
    }

    companion object {
        private const val KEY_ONBOARDING = "onboarding_done"
        private const val KEY_THEME = "theme"
        private const val KEY_LANG = "lang"
        private const val KEY_PUSH = "push"
        private const val KEY_RISK = "risk"
        private const val KEY_HABIT_REMINDERS = "habit_reminders"
        private const val KEY_DAILY = "daily"
        private const val KEY_DAILY_H = "daily_h"
        private const val KEY_DAILY_M = "daily_m"
        private const val KEY_WEEK_START = "week_start"
        private const val KEY_TIME_FORMAT = "time_format"
        private const val KEY_AUTO_SYNC = "auto_sync"
        private const val KEY_SYNC_MIN = "sync_min"
        private const val KEY_RISK_DATE = "risk_date"
        private const val KEY_NOTIF_PROMPT = "notif_prompt"
    }
}
