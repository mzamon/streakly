package com.streakly.app.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class StreakCalculatorTest {

    private val today = LocalDate.of(2026, 10, 27) // a Tuesday
    private fun days(vararg offsets: Int): Set<String> = offsets.map { today.minusDays(it.toLong()).toString() }.toSet()
    private val daily = Schedule(createdAt = "2026-09-01")

    @Test fun `streak counts consecutive days including today`() {
        assertEquals(3, StreakCalculator.currentStreak(daily, days(0, 1, 2), today))
    }

    @Test fun `unfinished today does not break the streak`() {
        assertEquals(2, StreakCalculator.currentStreak(daily, days(1, 2), today))
    }

    @Test fun `a missed day resets the streak`() {
        assertEquals(1, StreakCalculator.currentStreak(daily, days(0, 2, 3), today))
        assertEquals(0, StreakCalculator.currentStreak(daily, days(2, 3), today))
    }

    @Test fun `no completions means no streak`() {
        assertEquals(0, StreakCalculator.currentStreak(daily, emptySet(), today))
        assertEquals(0, StreakCalculator.bestStreak(daily, emptySet(), today))
    }

    @Test fun `best streak finds the longest run`() {
        // 5 in a row, a gap, then 2 in a row ending today
        assertEquals(5, StreakCalculator.bestStreak(daily, days(0, 1, 5, 6, 7, 8, 9), today))
    }

    @Test fun `specific days schedule only counts due days`() {
        // Mon, Wed, Fri habit. 2026-10-27 is Tuesday so today is not due.
        val mwf = Schedule(createdAt = "2026-09-01", frequencyType = "DAYS", daysOfWeek = "1,3,5")
        assertFalse(StreakCalculator.isDueOn(mwf, today, today))
        assertTrue(StreakCalculator.isDueOn(mwf, LocalDate.of(2026, 10, 26), today)) // Monday
        // Done Fri 23, Wed 21, Mon 19: streak is 3 (Tuesday/Thursday are not due so they do not break it)
        val done = setOf("2026-10-26", "2026-10-23", "2026-10-21")
        assertEquals(3, StreakCalculator.currentStreak(mwf, done, today))
    }

    @Test fun `interval schedule is due every N days from creation`() {
        val every3 = Schedule(createdAt = "2026-10-01", frequencyType = "INTERVAL", interval = 3)
        assertTrue(StreakCalculator.isDueOn(every3, LocalDate.of(2026, 10, 1), today))
        assertFalse(StreakCalculator.isDueOn(every3, LocalDate.of(2026, 10, 2), today))
        assertTrue(StreakCalculator.isDueOn(every3, LocalDate.of(2026, 10, 4), today))
    }

    @Test fun `habit is not due before it was created`() {
        assertFalse(StreakCalculator.isDueOn(Schedule(createdAt = "2026-10-20"), LocalDate.of(2026, 10, 19), today))
    }

    @Test fun `at risk when a streak is running and today is open`() {
        assertTrue(StreakCalculator.atRisk(daily, days(1, 2, 3), today))
        assertFalse(StreakCalculator.atRisk(daily, days(0, 1, 2), today))   // already done today
        assertFalse(StreakCalculator.atRisk(daily, days(2, 3), today))      // streak already broken
        assertFalse(StreakCalculator.atRisk(daily, emptySet(), today))
    }

    @Test fun `completion rate is done over due days`() {
        val from = today.minusDays(3)
        assertEquals(50, StreakCalculator.completionRate(daily, days(0, 2), from, today, today)) // 2 of 4
        assertEquals(0, StreakCalculator.completionRate(daily, emptySet(), from, today, today))
        assertEquals(100, StreakCalculator.completionRate(daily, days(0, 1, 2, 3), from, today, today))
    }

    @Test fun `completion rate never looks past today`() {
        assertEquals(100, StreakCalculator.completionRate(daily, days(0), today, today.plusDays(10), today))
    }

    @Test fun `stats bundle everything`() {
        val stats = StreakCalculator.stats(daily, days(0, 1, 2), today)
        assertEquals(3, stats.currentStreak)
        assertEquals(3, stats.bestStreak)
        assertEquals(3, stats.totalCompletions)
    }

    @Test fun `malformed created date falls back to today`() {
        val broken = Schedule(createdAt = "not-a-date")
        assertTrue(StreakCalculator.isDueOn(broken, today, today))
        assertEquals(1, StreakCalculator.currentStreak(broken, days(0), today))
    }
}
