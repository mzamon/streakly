package com.streakly.app.utils

import com.streakly.app.data.model.Habit
import com.streakly.app.data.model.StreakStats
import java.time.LocalDate

object StreakCalculator {

    fun isDueOn(habit: Habit, date: LocalDate): Boolean {
        if (date.isBefore(parseOrNull(habit.createdAt) ?: LocalDate.MIN)) return false
        return when (habit.frequencyType) {
            "DAYS" -> {
                val set = habit.daysOfWeek.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
                set.contains(date.dayOfWeek.value)
            }
            "INTERVAL" -> {
                val created = parseOrNull(habit.createdAt) ?: return false
                val days = java.time.temporal.ChronoUnit.DAYS.between(created, date).toInt()
                days >= 0 && days % habit.interval.coerceAtLeast(1) == 0
            }
            else -> true
        }
    }

    fun currentStreak(habit: Habit, dates: Set<String>): Int {
        var count = 0
        var cursor = LocalDate.now()
        if (!isDueOn(habit, cursor) || !dates.contains(cursor.toString())) {
            cursor = cursor.minusDays(1)
            while (isDueOn(habit, cursor) && !dates.contains(cursor.toString())) {
                cursor = cursor.minusDays(1)
            }
        }
        while (dates.contains(cursor.toString())) {
            count++
            cursor = cursor.minusDays(1)
            while (!isDueOn(habit, cursor)) cursor = cursor.minusDays(1)
        }
        return count
    }

    fun bestStreak(habit: Habit, dates: Set<String>): Int {
        if (dates.isEmpty()) return 0
        val created = parseOrNull(habit.createdAt) ?: return 0
        var best = 0
        var run = 0
        var cursor = created
        val today = LocalDate.now()
        while (!cursor.isAfter(today)) {
            if (isDueOn(habit, cursor)) {
                if (dates.contains(cursor.toString())) {
                    run++
                    if (run > best) best = run
                } else if (cursor.isBefore(today)) {
                    run = 0
                }
            }
            cursor = cursor.plusDays(1)
        }
        return best
    }

    fun brokeStreakYesterday(habit: Habit, dates: Set<String>): Boolean {
        val y = LocalDate.now().minusDays(1)
        return isDueOn(habit, y) && !dates.contains(y.toString())
    }

    fun stats(habit: Habit, dates: Set<String>): StreakStats {
        val created = parseOrNull(habit.createdAt) ?: LocalDate.now()
        val today = LocalDate.now()
        var dueCount = 0
        var doneCount = 0
        var cursor = created
        while (!cursor.isAfter(today)) {
            if (isDueOn(habit, cursor)) {
                dueCount++
                if (dates.contains(cursor.toString())) doneCount++
            }
            cursor = cursor.plusDays(1)
        }
        val rate = if (dueCount == 0) 0 else ((doneCount.toFloat() / dueCount) * 100).toInt().coerceAtMost(100)
        return StreakStats(
            currentStreak = currentStreak(habit, dates),
            bestStreak = bestStreak(habit, dates),
            totalCompletions = dates.size,
            completionRate = rate
        )
    }

    private fun parseOrNull(s: String): LocalDate? =
        try { if (s.isEmpty()) null else LocalDate.parse(s) } catch (e: Exception) { null }
}
