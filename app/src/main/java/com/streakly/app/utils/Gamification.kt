package com.streakly.app.utils

/** Points, levels and badges. Pure functions so the rules are covered by unit tests. */
object Gamification {

    const val XP_PER_COMPLETION = 10
    const val XP_PER_LEVEL = 100

    data class Badge(val id: String, val icon: String, val earned: Boolean)

    fun xpForCompletions(count: Int): Int = count.coerceAtLeast(0) * XP_PER_COMPLETION

    fun levelForXp(xp: Int): Int = 1 + xp.coerceAtLeast(0) / XP_PER_LEVEL

    fun xpIntoLevel(xp: Int): Int = xp.coerceAtLeast(0) % XP_PER_LEVEL

    fun xpToNextLevel(xp: Int): Int = XP_PER_LEVEL - xpIntoLevel(xp)

    /** Spendable points = lifetime XP minus points spent on rewards (never negative). */
    fun balance(xp: Int, spent: Int): Int = (xp - spent).coerceAtLeast(0)

    fun canAfford(balance: Int, cost: Int): Boolean = cost > 0 && balance >= cost

    fun badges(
        totalCompletions: Int,
        bestStreak: Int,
        habitCount: Int,
        redemptions: Int
    ): List<Badge> = listOf(
        Badge("first_flame", "ic_flame", totalCompletions >= 1),
        Badge("streak_7", "ic_cloud", bestStreak >= 7),
        Badge("streak_30", "ic_trophy", bestStreak >= 30),
        Badge("century", "ic_star", totalCompletions >= 100),
        Badge("collector", "ic_file", habitCount >= 5),
        Badge("treat_yourself", "ic_gift", redemptions >= 1)
    )
}
