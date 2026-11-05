package com.streakly.app.utils

import android.content.Context
import android.text.format.DateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale

object DateUtils {
    fun monthTitle(month: YearMonth): String =
        month.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()))

    fun fullDate(date: LocalDate): String =
        date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault()))

    fun fullDate(iso: String): String =
        try { fullDate(LocalDate.parse(iso.take(10))) } catch (e: Exception) { iso }

    fun is24Hour(context: Context, preference: String): Boolean = when (preference) {
        "24" -> true
        "12" -> false
        else -> DateFormat.is24HourFormat(context)
    }

    fun formatTime(context: Context, hour: Int, minute: Int, preference: String): String {
        val pattern = if (is24Hour(context, preference)) "HH:mm" else "h:mm a"
        return LocalTime.of(hour.coerceIn(0, 23), minute.coerceIn(0, 59))
            .format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))
    }

    /** Week-day labels in display order, honouring the chosen first day of the week. */
    fun weekdayLabels(weekStart: String): List<String> {
        val first = if (weekStart == "SUNDAY") DayOfWeek.SUNDAY else DayOfWeek.MONDAY
        return (0..6).map { first.plus(it.toLong()).getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
    }

    /** Number of empty cells before day 1 of [month] in a grid that starts on [weekStart]. */
    fun leadingBlanks(month: YearMonth, weekStart: String): Int = CalendarMath.leadingBlanks(month, weekStart)


    /** First day of the week containing [date], honouring the chosen week start ("MONDAY" or "SUNDAY"). */
    fun startOfWeek(date: LocalDate, weekStart: String): LocalDate = CalendarMath.startOfWeek(date, weekStart)
}
