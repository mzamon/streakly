package com.streakly.app.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.YearMonth

class DateUtilsTest {
    @Test
    fun `date helpers format month and dates`() {
        assertEquals("October 2026", DateUtils.monthTitle(YearMonth.of(2026, 10)))
        assertEquals(DateUtils.fullDate(LocalDate.of(2026, 10, 27)), DateUtils.fullDate("2026-10-27"))
        assertEquals("not-a-date", DateUtils.fullDate("not-a-date"))
    }

    @Test
    fun `weekday labels follow selected week start`() {
        assertEquals("Mon", DateUtils.weekdayLabels("MONDAY").first())
        assertEquals("Sun", DateUtils.weekdayLabels("SUNDAY").first())
        assertEquals(7, DateUtils.weekdayLabels("MONDAY").size)
    }

    @Test
    fun `calendar delegates preserve date calculations`() {
        val date = LocalDate.of(2026, 10, 27)
        assertEquals(CalendarMath.leadingBlanks(YearMonth.of(2026, 10), "SUNDAY"), DateUtils.leadingBlanks(YearMonth.of(2026, 10), "SUNDAY"))
        assertEquals(CalendarMath.startOfWeek(date, "MONDAY"), DateUtils.startOfWeek(date, "MONDAY"))
    }
}
