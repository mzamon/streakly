package com.streakly.app.data.model

data class Redemption(
    val id: Long = 0,
    val rewardId: Long,
    val rewardName: String,
    val cost: Int,
    val date: String
)
