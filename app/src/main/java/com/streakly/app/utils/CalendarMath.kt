package com.streakly.app.utils

import java.time.LocalDate
import java.time.YearMonth

/** Calendar arithmetic with no Android dependencies (unit tested). */
object CalendarMath {
    /** Number of empty cells before day 1 of [month] in a grid that starts on [weekStart] ("MONDAY" or "SUNDAY"). */
    fun leadingBlanks(month: YearMonth, weekStart: String): Int {
        val dow = month.atDay(1).dayOfWeek.value // Monday = 1 ... Sunday = 7
        return if (weekStart == "SUNDAY") dow % 7 else dow - 1
    }

    /** First day of the week that contains [date]. */
    fun startOfWeek(date: LocalDate, weekStart: String): LocalDate {
        val offset = if (weekStart == "SUNDAY") date.dayOfWeek.value % 7 else date.dayOfWeek.value - 1
        return date.minusDays(offset.toLong())
    }
}
