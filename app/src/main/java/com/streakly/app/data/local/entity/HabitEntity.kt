package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val remoteId: String? = null,
    val name: String,
    val description: String = "",
    val notes: String = "",
    val color: String,
    val icon: String,
    val frequencyType: String,
    val daysOfWeek: String = "",
    val interval: Int = 1,
    val reminderHour: Int = -1,
    val reminderMinute: Int = 0,
    val createdAt: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val pendingSync: Boolean = true
)
