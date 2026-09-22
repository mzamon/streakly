package com.streakly.app.data.model

data class Habit(
    val localId: Long = 0,
    val remoteId: String? = null,
    val name: String,
    val description: String = "",
    val notes: String = "",
    val color: String = "#F2994A",
    val icon: String = "🔥",
    val frequencyType: String = "DAILY",
    val daysOfWeek: String = "",
    val interval: Int = 1,
    val reminderHour: Int = -1,
    val reminderMinute: Int = 0,
    val createdAt: String = "",
    val updatedAt: Long = System.currentTimeMillis(),
    val pendingSync: Boolean = true
)