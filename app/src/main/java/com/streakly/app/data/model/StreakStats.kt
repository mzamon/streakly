package com.streakly.app.data.model

data class StreakStats(
    val currentStreak: Int,
    val bestStreak: Int,
    val totalCompletions: Int,
    val completionRate: Int
)