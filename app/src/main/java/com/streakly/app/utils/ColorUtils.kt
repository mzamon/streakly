package com.streakly.app.utils

import android.graphics.Color
import android.content.Context
import com.streakly.app.R

object ColorUtils {
    val HABIT_COLORS = listOf("#FF7A00", "#FFB547", "#20C7B7", "#4C8DFF", "#9B6BFF", "#FF5C8A", "#7ED957", "#8A99B3")
    val HABIT_ICONS = listOf(
        "ic_cloud", "ic_flame", "ic_file", "ic_settings", "ic_gift", "ic_moon",
        "ic_user", "ic_edit", "ic_star", "ic_camera", "ic_calendar", "ic_globe",
        "ic_trash", "ic_shield", "ic_help", "ic_trophy"
    )
    val REWARD_ICONS = listOf(
        "ic_gift", "ic_clock", "ic_file", "ic_star", "ic_moon",
        "ic_settings", "ic_star", "ic_globe", "ic_cloud", "ic_flame"
    )

    private val legacyIcons = mapOf(
        "💧" to "ic_cloud", "🏃" to "ic_flame", "📚" to "ic_file", "🧘" to "ic_settings",
        "🥗" to "ic_gift", "😴" to "ic_moon", "💪" to "ic_user", "✍️" to "ic_edit",
        "🎯" to "ic_star", "🎨" to "ic_camera", "🎵" to "ic_calendar", "💻" to "ic_globe",
        "🧹" to "ic_trash", "🚭" to "ic_shield", "🙏" to "ic_help", "🔥" to "ic_flame",
        "🎁" to "ic_gift", "☕" to "ic_clock", "🎬" to "ic_file", "🍕" to "ic_star",
        "🛋️" to "ic_moon", "🎮" to "ic_settings", "🍦" to "ic_star", "🛍️" to "ic_globe",
        "🎧" to "ic_cloud", "🌴" to "ic_flame"
    )

    fun iconResource(context: Context, value: String): Int {
        val name = legacyIcons[value] ?: value
        return context.resources.getIdentifier(name, "drawable", context.packageName)
            .takeIf { it != 0 } ?: R.drawable.ic_flame
    }

    /** Parses "#RRGGBB"; falls back to Streakly orange when the stored value is malformed. */
    fun parse(hex: String): Int = try {
        Color.parseColor(hex)
    } catch (e: Exception) {
        Color.parseColor("#FF7A00")
    }

    fun withAlpha(color: Int, alpha: Int): Int =
        Color.argb(alpha.coerceIn(0, 255), Color.red(color), Color.green(color), Color.blue(color))
}
