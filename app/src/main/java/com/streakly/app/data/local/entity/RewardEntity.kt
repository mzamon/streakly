package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "rewards", indices = [Index("uid")])
data class RewardEntity(
    @PrimaryKey val id: String,
    val uid: String,
    val name: String,
    val description: String = "",
    val cost: Int,
    val icon: String = "🎁",
    val updatedAt: Long = System.currentTimeMillis(),
    val serverVersion: Long = 0L,
    val syncState: String = SyncState.PENDING,
    val deleted: Boolean = false
)
