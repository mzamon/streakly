package com.streakly.app.utils

import java.time.LocalDate

/** Aggregates "done / due" counts across habits for the My Progress screen. */
object StatsCalculator {

    data class Counts(val due: Int, val done: Int) {
        val percent: Int get() = if (due == 0) 0 else ((done * 100f) / due).toInt().coerceIn(0, 100)
    }

    /**
     * @param schedules habit id -> schedule
     * @param doneDates habit id -> ISO dates completed
     */
    fun counts(
        schedules: Map<String, Schedule>,
        doneDates: Map<String, Set<String>>,
        from: LocalDate,
        to: LocalDate,
        today: LocalDate = LocalDate.now()
    ): Counts {
        val end = if (to.isAfter(today)) today else to
        var due = 0
        var done = 0
        var cursor = from
        var guard = 0
        while (!cursor.isAfter(end) && guard < 400) {
            for ((id, schedule) in schedules) {
                if (StreakCalculator.isDueOn(schedule, cursor, today)) {
                    due++
                    if (doneDates[id]?.contains(cursor.toString()) == true) done++
                }
            }
            cursor = cursor.plusDays(1)
            guard++
        }
        return Counts(due, done)
    }
}
