package com.streakly.app.data.model

import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity
import com.streakly.app.utils.Gamification
import com.streakly.app.utils.Schedule
import com.streakly.app.utils.StreakCalculator
import com.streakly.app.utils.StreakStats
import java.time.LocalDate

fun HabitEntity.schedule(): Schedule = Schedule(createdAt, frequencyType, daysOfWeek, interval)

/** Everything the screens need, derived from one consistent read of the local database. */
data class AppSnapshot(
    val habits: List<HabitEntity>,
    val logs: List<HabitLogEntity>,
    val rewards: List<RewardEntity>,
    val redemptions: List<RedemptionEntity>
) {
    private val habitIds: Set<String> = habits.map { it.id }.toSet()

    /** habitId -> ISO dates the habit was completed. Logs of deleted habits are ignored. */
    val completions: Map<String, Set<String>> =
        logs.filter { it.completed && habitIds.contains(it.habitId) }
            .groupBy { it.habitId }
            .mapValues { entry -> entry.value.map { it.date }.toSet() }

    val totalCompletions: Int = completions.values.sumOf { it.size }
    val xp: Int = Gamification.xpForCompletions(totalCompletions)
    val spent: Int = redemptions.sumOf { it.cost }
    val balance: Int = Gamification.balance(xp, spent)
    val level: Int = Gamification.levelForXp(xp)

    fun doneDates(habitId: String): Set<String> = completions[habitId] ?: emptySet()

    fun isDone(habitId: String, date: LocalDate): Boolean = doneDates(habitId).contains(date.toString())

    fun statsFor(habit: HabitEntity, today: LocalDate = LocalDate.now()): StreakStats =
        StreakCalculator.stats(habit.schedule(), doneDates(habit.id), today)

    fun dueOn(date: LocalDate): List<HabitEntity> =
        habits.filter { StreakCalculator.isDueOn(it.schedule(), date, LocalDate.now()) }

    /** The longest streak currently running across all habits. */
    fun currentStreak(today: LocalDate = LocalDate.now()): Int =
        habits.maxOfOrNull { StreakCalculator.currentStreak(it.schedule(), doneDates(it.id), today) } ?: 0

    fun bestStreak(today: LocalDate = LocalDate.now()): Int =
        habits.maxOfOrNull { StreakCalculator.bestStreak(it.schedule(), doneDates(it.id), today) } ?: 0

    /** Habits that have a running streak but are not logged yet today. */
    fun atRisk(today: LocalDate = LocalDate.now()): List<HabitEntity> =
        habits.filter { StreakCalculator.atRisk(it.schedule(), doneDates(it.id), today) }

    fun badges(): List<Gamification.Badge> =
        Gamification.badges(totalCompletions, bestStreak(), habits.size, redemptions.size)

    companion object {
        val EMPTY = AppSnapshot(emptyList(), emptyList(), emptyList(), emptyList())
    }
}
