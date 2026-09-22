package com.streakly.app.data.model

data class Habit(
    val localId: Long = 0L,
    val remoteId: String? = null,
    val name: String,
    val description: String = "",
    val color: String = "#F2994A",
    val icon: String = "🔥",
    val frequencyType: String = "DAILY", // DAILY, DAYS, INTERVAL
    val daysOfWeek: String = "", // Comma-separated integers e.g. "1,3,5" (Mon, Wed, Fri)
    val interval: Int = 1,
    val reminderHour: Int = -1,
    val reminderMinute: Int = 0,
    val notes: String = "",
    val createdAt: String = java.time.LocalDate.now().toString(),
    val isPendingSync: Boolean = false,
    val isDeleted: Boolean = false
)