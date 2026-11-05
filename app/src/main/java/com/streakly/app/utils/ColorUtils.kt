package com.streakly.app.utils

import android.graphics.Color

object ColorUtils {
    val HABIT_COLORS = listOf("#FF7A00", "#FFB547", "#20C7B7", "#4C8DFF", "#9B6BFF", "#FF5C8A", "#7ED957", "#8A99B3")
    val HABIT_ICONS = listOf("💧", "🏃", "📚", "🧘", "🥗", "😴", "💪", "✍️", "🎯", "🎨", "🎵", "💻", "🧹", "🚭", "🙏", "🔥")
    val REWARD_ICONS = listOf("🎁", "☕", "🎬", "🍕", "🛋️", "🎮", "🍦", "🛍️", "🎧", "🌴")

    /** Parses "#RRGGBB"; falls back to Streakly orange when the stored value is malformed. */
    fun parse(hex: String): Int = try {
        Color.parseColor(hex)
    } catch (e: Exception) {
        Color.parseColor("#FF7A00")
    }

    fun withAlpha(color: Int, alpha: Int): Int =
        Color.argb(alpha.coerceIn(0, 255), Color.red(color), Color.green(color), Color.blue(color))
}
