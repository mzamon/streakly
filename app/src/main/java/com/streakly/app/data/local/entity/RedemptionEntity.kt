package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "redemptions")
data class RedemptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val remoteId: String? = null,
    val rewardId: Long,
    val rewardName: String,
    val cost: Int,
    val date: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val pendingSync: Boolean = true
)
