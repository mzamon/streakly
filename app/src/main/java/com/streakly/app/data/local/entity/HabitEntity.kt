package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A habit. The id is a client generated UUID so habits can be created while offline.
 * frequencyType: DAILY, DAYS (daysOfWeek holds ISO day numbers "1,3,5") or INTERVAL (every [interval] days).
 */
@Entity(tableName = "habits", indices = [Index("uid")])
data class HabitEntity(
    @PrimaryKey val id: String,
    val uid: String,
    val name: String,
    val description: String = "",
    val notes: String = "",
    val icon: String = "ic_flame",
    val color: String = "#FF7A00",
    val frequencyType: String = "DAILY",
    val daysOfWeek: String = "",
    val interval: Int = 1,
    val reminderHour: Int = -1,
    val reminderMinute: Int = 0,
    val createdAt: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val serverVersion: Long = 0L,
    val syncState: String = SyncState.PENDING,
    val deleted: Boolean = false
)
