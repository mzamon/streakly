package com.streakly.app.utils

import java.time.Duration
import java.time.LocalDateTime

object ReminderTime {
    /** Milliseconds from [now] until the next occurrence of hour:minute (always at least ~30 s in the future). */
    fun delayUntil(hour: Int, minute: Int, now: LocalDateTime = LocalDateTime.now()): Long {
        var next = now.toLocalDate().atTime(hour.coerceIn(0, 23), minute.coerceIn(0, 59))
        if (!next.isAfter(now.plusSeconds(30))) next = next.plusDays(1)
        return Duration.between(now, next).toMillis()
    }
}
