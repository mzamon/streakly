package com.streakly.app.data.model

data class HabitLog(
    val localId: Long = 0L,
    val habitLocalId: Long,
    val date: String, // YYYY-MM-DD
    val isPendingSync: Boolean = false,
    val isDeleted: Boolean = false
)