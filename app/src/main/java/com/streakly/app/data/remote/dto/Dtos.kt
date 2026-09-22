package com.streakly.app.data.remote.dto

data class HabitDto(
    val remoteId: String,
    val name: String,
    val description: String,
    val notes: String,
    val color: String,
    val icon: String,
    val frequencyType: String,
    val daysOfWeek: String,
    val interval: Int,
    val reminderHour: Int,
    val reminderMinute: Int,
    val createdAt: String
)

data class LogDto(
    val habitRemoteId: String,
    val date: String,
    val completed: Boolean,
    val note: String
)

data class SyncRequest(val habits: List<HabitDto>, val logs: List<LogDto>)
data class SyncResponse(val habits: List<HabitDto>, val logs: List<LogDto>, val serverTime: Long)
