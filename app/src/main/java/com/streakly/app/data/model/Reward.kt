package com.streakly.app.data.model

data class Reward(
    val localId: Long = 0L,
    val remoteId: String? = null,
    val name: String,
    val description: String = "",
    val cost: Int,
    val icon: String = "🎁",
    val isPendingSync: Boolean = false,
    val isDeleted: Boolean = false
)