package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/** One row per habit per day. The id is deterministic ("habitId|yyyy-MM-dd") so two devices never duplicate a day. */
@Entity(tableName = "habit_logs", indices = [Index("uid"), Index("habitId")])
data class HabitLogEntity(
    @PrimaryKey val id: String,
    val uid: String,
    val habitId: String,
    val date: String,
    val completed: Boolean = true,
    val note: String = "",
    val updatedAt: Long = System.currentTimeMillis(),
    val serverVersion: Long = 0L,
    val syncState: String = SyncState.PENDING,
    val deleted: Boolean = false
) {
    companion object {
        fun idFor(habitId: String, date: String): String = "$habitId|$date"
    }
}
