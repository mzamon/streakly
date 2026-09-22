package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class RewardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val remoteId: String? = null,
    val name: String,
    val description: String = "",
    val cost: Int,
    val icon: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val pendingSync: Boolean = true
)
