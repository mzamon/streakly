package com.streakly.app.ui.rewards

import com.streakly.app.R

/** Maps a badge id to its translated title and "how to earn it" text. */
object BadgeText {
    fun title(id: String): Int = when (id) {
        "first_flame" -> R.string.badge_first_flame
        "streak_7" -> R.string.badge_streak_7
        "streak_30" -> R.string.badge_streak_30
        "century" -> R.string.badge_century
        "collector" -> R.string.badge_collector
        else -> R.string.badge_treat
    }

    fun description(id: String): Int = when (id) {
        "first_flame" -> R.string.badge_first_flame_desc
        "streak_7" -> R.string.badge_streak_7_desc
        "streak_30" -> R.string.badge_streak_30_desc
        "century" -> R.string.badge_century_desc
        "collector" -> R.string.badge_collector_desc
        else -> R.string.badge_treat_desc
    }
}
