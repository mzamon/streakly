package com.streakly.app.data.model

data class Reward(
    val id: Long = 0,
    val remoteId: String? = null,
    val name: String,
    val description: String = "",
    val cost: Int,
    val icon: String = "🏅"
)
