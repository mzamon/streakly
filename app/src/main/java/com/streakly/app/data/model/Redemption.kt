package com.streakly.app.data.model

data class Redemption(
    val localId: Long = 0L,
    val remoteId: String? = null,
    val rewardLocalId: Long,
    val rewardName: String,
    val cost: Int,
    val date: String, // YYYY-MM-DD
    val isPendingSync: Boolean = false
)