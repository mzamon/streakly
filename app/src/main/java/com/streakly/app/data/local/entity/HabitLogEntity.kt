package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "habit_logs", indices = [Index(value = ["habitId", "date"], unique = true)])
data class HabitLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val habitId: Long,
    val date: String,
    val completed: Boolean = true,
    val note: String = "",
    val updatedAt: Long = System.currentTimeMillis(),
    val pendingSync: Boolean = true
)
