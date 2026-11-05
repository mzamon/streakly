package com.streakly.app.push

/** Extras used to route a notification tap to the right screen (deep link). */
object AppIntents {
    const val EXTRA_DEST = "streakly.dest"
    const val EXTRA_HABIT_ID = "streakly.habit_id"
    const val DEST_HABIT = "habit"
    const val DEST_REWARDS = "rewards"
    const val DEST_SYNC = "sync"
    const val DEST_HOME = "home"
}
