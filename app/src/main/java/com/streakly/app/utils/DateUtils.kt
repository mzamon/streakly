package com.streakly.app.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    private val prettyFmt = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH)
    fun today(): String = LocalDate.now().toString()
    fun pretty(iso: String): String =
        try { LocalDate.parse(iso).format(prettyFmt) } catch (e: Exception) { iso }
}