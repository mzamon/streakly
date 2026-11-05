package com.streakly.app.utils

import java.time.LocalDate
import java.time.temporal.ChronoUnit

/** The scheduling part of a habit, kept free of Android / Room types so it is trivial to unit test. */
data class Schedule(
    val createdAt: String,
    val frequencyType: String = "DAILY",
    val daysOfWeek: String = "",
    val interval: Int = 1
)

data class StreakStats(
    val currentStreak: Int,
    val bestStreak: Int,
    val totalCompletions: Int,
    val completionRate: Int
)

object StreakCalculator {
    private const val MAX_DAYS = 4000

    private fun parse(s: String): LocalDate? =
        try { if (s.isBlank()) null else LocalDate.parse(s.take(10)) } catch (e: Exception) { null }

    private fun start(schedule: Schedule, today: LocalDate): LocalDate = parse(schedule.createdAt) ?: today

    fun isDueOn(schedule: Schedule, date: LocalDate, today: LocalDate = LocalDate.now()): Boolean {
        val created = start(schedule, today)
        if (date.isBefore(created)) return false
        return when (schedule.frequencyType) {
            "DAYS" -> {
                val days = schedule.daysOfWeek.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
                days.contains(date.dayOfWeek.value)
            }
            "INTERVAL" -> {
                val gap = ChronoUnit.DAYS.between(created, date)
                gap >= 0 && gap % schedule.interval.coerceAtLeast(1) == 0L
            }
            else -> true
        }
    }

    /**
     * Consecutive due days completed, counting back from today. Today being unfinished does not break the
     * streak (the user still has until midnight), but any earlier missed due day does.
     */
    fun currentStreak(schedule: Schedule, done: Set<String>, today: LocalDate = LocalDate.now()): Int {
        val created = start(schedule, today)
        var cursor = today
        if (isDueOn(schedule, today, today) && !done.contains(today.toString())) cursor = today.minusDays(1)
        var count = 0
        var guard = 0
        while (!cursor.isBefore(created) && guard < MAX_DAYS) {
            if (isDueOn(schedule, cursor, today)) {
                if (done.contains(cursor.toString())) count++ else break
            }
            cursor = cursor.minusDays(1)
            guard++
        }
        return count
    }

    fun bestStreak(schedule: Schedule, done: Set<String>, today: LocalDate = LocalDate.now()): Int {
        if (done.isEmpty()) return 0
        var cursor = start(schedule, today)
        var best = 0
        var run = 0
        var guard = 0
        while (!cursor.isAfter(today) && guard < MAX_DAYS) {
            if (isDueOn(schedule, cursor, today)) {
                if (done.contains(cursor.toString())) {
                    run++
                    if (run > best) best = run
                } else if (cursor.isBefore(today)) {
                    run = 0
                }
            }
            cursor = cursor.plusDays(1)
            guard++
        }
        return best
    }

    /** True when the user has a running streak and today's occurrence is still not logged. */
    fun atRisk(schedule: Schedule, done: Set<String>, today: LocalDate = LocalDate.now()): Boolean =
        isDueOn(schedule, today, today) &&
            !done.contains(today.toString()) &&
            currentStreak(schedule, done, today) > 0

    /** Percentage (0-100) of due days completed between [from] and [to] inclusive (never beyond today). */
    fun completionRate(
        schedule: Schedule,
        done: Set<String>,
        from: LocalDate,
        to: LocalDate,
        today: LocalDate = LocalDate.now()
    ): Int {
        val end = if (to.isAfter(today)) today else to
        var cursor = maxOf(from, start(schedule, today))
        var due = 0
        var completed = 0
        var guard = 0
        while (!cursor.isAfter(end) && guard < MAX_DAYS) {
            if (isDueOn(schedule, cursor, today)) {
                due++
                if (done.contains(cursor.toString())) completed++
            }
            cursor = cursor.plusDays(1)
            guard++
        }
        return if (due == 0) 0 else ((completed * 100f) / due).toInt().coerceIn(0, 100)
    }

    fun stats(schedule: Schedule, done: Set<String>, today: LocalDate = LocalDate.now()): StreakStats =
        StreakStats(
            currentStreak = currentStreak(schedule, done, today),
            bestStreak = bestStreak(schedule, done, today),
            totalCompletions = done.size,
            completionRate = completionRate(schedule, done, start(schedule, today), today, today)
        )
}
