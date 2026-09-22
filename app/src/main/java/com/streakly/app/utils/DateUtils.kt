package com.streakly.app.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    fun today(): String = LocalDate.now().toString()

    fun pretty(dateStr: String): String {
        return try {
            val date = LocalDate.parse(dateStr)
            date.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy"))
        } catch (e: Exception) {
            dateStr
        }
    }
}