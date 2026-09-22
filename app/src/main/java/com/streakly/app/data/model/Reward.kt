package com.streakly.app.data.model

data class Reward(
    val id: Long = 0,
    val remoteId: String? = null,
    val name: String,
    val description: String = "",
    val cost: Int,
    val icon: String = "🏅"
)

data class Redemption(
    val id: Long = 0,
    val rewardId: Long,
    val rewardName: String,
    val cost: Int,
    val date: String
)