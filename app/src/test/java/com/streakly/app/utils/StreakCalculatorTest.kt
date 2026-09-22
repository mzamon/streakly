package com.streakly.app.utils

import com.streakly.app.data.model.Habit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class StreakCalculatorTest {

    private fun habit(type: String = "DAILY", days: String = "", interval: Int = 3,
                      created: String = "2026-01-01") =
        Habit(name = "Test", frequencyType = type, daysOfWeek = days,
              interval = interval, createdAt = created)

    private fun dates(vararg ds: String) = ds.toSet()

    @Test
    fun `daily habit is due every day`() {
        assertTrue(StreakCalculator.isDueOn(habit(), LocalDate.of(2026, 3, 15)))
    }

    @Test
    fun `specific days respects weekday`() {
        val h = habit(type = "DAYS", days = "1,3,5") // Mon, Wed, Fri
        assertTrue(StreakCalculator.isDueOn(h, LocalDate.of(2026, 3, 16))) // Monday
        assertFalse(StreakCalculator.isDueOn(h, LocalDate.of(2026, 3, 17))) // Tuesday
    }

    @Test
    fun `interval habit is due every N days from creation`() {
        val h = habit(type = "INTERVAL", interval = 3, created = "2026-03-01")
        assertTrue(StreakCalculator.isDueOn(h, LocalDate.of(2026, 3, 1)))
        assertTrue(StreakCalculator.isDueOn(h, LocalDate.of(2026, 3, 4)))
        assertFalse(StreakCalculator.isDueOn(h, LocalDate.of(2026, 3, 2)))
    }

    @Test
    fun `current streak counts consecutive due days ending today`() {
        val today = LocalDate.now().toString()
        val yesterday = LocalDate.now().minusDays(1).toString()
        val twoDaysAgo = LocalDate.now().minusDays(2).toString()
        val streak = StreakCalculator.currentStreak(habit(), dates(today, yesterday, twoDaysAgo))
        assertTrue(streak >= 3)
    }

    @Test
    fun `missed due day breaks the chain`() {
        val today = LocalDate.now().toString()
        val twoDaysAgo = LocalDate.now().minusDays(2).toString()
        val streak = StreakCalculator.currentStreak(habit(), dates(today, twoDaysAgo))
        assertEquals(1, streak)
    }

    @Test
    fun `best streak finds the longest run`() {
        val run = (0L..6L).map { LocalDate.now().minusDays(it).toString() }.toSet()
        val best = StreakCalculator.bestStreak(habit(), run)
        assertTrue(best >= 7)
    }

    @Test
    fun `stats completion rate caps at 100`() {
        val today = LocalDate.now().toString()
        val stats = StreakCalculator.stats(habit(created = today), dates(today))
        assertEquals(100, stats.completionRate)
    }
}