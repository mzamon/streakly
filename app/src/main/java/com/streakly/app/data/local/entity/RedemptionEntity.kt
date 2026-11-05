package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "redemptions", indices = [Index("uid")])
/** Immutable-looking history entry for spending points on a reward. */
data class RedemptionEntity(
    @PrimaryKey val id: String,
    val uid: String,
    val rewardId: String,
    val rewardName: String,
    val cost: Int,
    val date: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val serverVersion: Long = 0L,
    val syncState: String = SyncState.PENDING,
    val deleted: Boolean = false
)
