package com.streakly.app.utils

import com.streakly.app.data.model.Habit
import com.streakly.app.data.model.StreakStats

object Gamification {

    const val POINTS_PER_COMPLETION = 10
    const val LEVEL_STEP = 100

    data class Badge(
        val id: String,
        val name: String,
        val emoji: String,
        val earned: Boolean,
        val description: String
    )

    fun pointsForCompletionCount(count: Int): Int = count * POINTS_PER_COMPLETION

    fun levelForPoints(points: Int): Int = 1 + (points / LEVEL_STEP)

    fun pointsToNextLevel(points: Int): Int = LEVEL_STEP - (points % LEVEL_STEP)

    fun badgesFor(stats: List<Triple<Habit, Set<String>, StreakStats>>): List<Badge> {
        val bestEver = stats.maxOfOrNull { it.third.bestStreak } ?: 0
        val totalDone = stats.sumOf { it.third.totalCompletions }
        return listOf(
            Badge("first", "First flame", "🔥", totalDone >= 1, "Complete one habit"),
            Badge("week", "7-day streak", "🌱", bestEver >= 7, "Complete 7 days in a row"),
            Badge("month", "30-day streak", "🏆", bestEver >= 30, "Complete 30 days in a row"),
            Badge("century", "100 completions", "⭐", totalDone >= 100, "Log 100 completions")
        )
    }
}
