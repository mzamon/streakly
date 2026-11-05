package com.streakly.app.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

class CalendarAndStatsTest {
    @Test fun `leading blanks for a monday-first grid`() {
        // 1 October 2026 is a Thursday
        assertEquals(3, CalendarMath.leadingBlanks(YearMonth.of(2026, 10), "MONDAY"))
        // 1 June 2026 is a Monday
        assertEquals(0, CalendarMath.leadingBlanks(YearMonth.of(2026, 6), "MONDAY"))
    }

    @Test fun `leading blanks for a sunday-first grid`() {
        assertEquals(4, CalendarMath.leadingBlanks(YearMonth.of(2026, 10), "SUNDAY"))
        // 1 November 2026 is a Sunday
        assertEquals(0, CalendarMath.leadingBlanks(YearMonth.of(2026, 11), "SUNDAY"))
    }

    @Test fun `start of week respects the chosen first day`() {
        val tuesday = LocalDate.of(2026, 10, 27)
        assertEquals(LocalDate.of(2026, 10, 26), CalendarMath.startOfWeek(tuesday, "MONDAY"))
        assertEquals(LocalDate.of(2026, 10, 25), CalendarMath.startOfWeek(tuesday, "SUNDAY"))
        assertEquals(LocalDate.of(2026, 10, 25), CalendarMath.startOfWeek(LocalDate.of(2026, 10, 25), "SUNDAY"))
    }

    @Test fun `reminder delay targets the next occurrence`() {
        val now = LocalDateTime.of(2026, 10, 27, 7, 0, 0)
        assertEquals(60L * 60 * 1000, ReminderTime.delayUntil(8, 0, now))             // later today
        assertEquals(23L * 60 * 60 * 1000, ReminderTime.delayUntil(6, 0, now))        // already passed -> tomorrow
        assertEquals(24L * 60 * 60 * 1000, ReminderTime.delayUntil(7, 0, now))        // exactly now -> tomorrow
    }

    @Test fun `aggregate counts across habits`() {
        val today = LocalDate.of(2026, 10, 27)
        val schedules = mapOf("a" to Schedule("2026-10-20"), "b" to Schedule("2026-10-20"))
        val done = mapOf(
            "a" to setOf("2026-10-26", "2026-10-27"),
            "b" to setOf("2026-10-27")
        )
        val counts = StatsCalculator.counts(schedules, done, today.minusDays(1), today, today)
        assertEquals(4, counts.due)
        assertEquals(3, counts.done)
        assertEquals(75, counts.percent)
        assertEquals(0, StatsCalculator.counts(emptyMap(), done, today, today, today).percent)
    }
}
