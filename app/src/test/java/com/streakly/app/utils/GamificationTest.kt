package com.streakly.app.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GamificationTest {

    @Test
    fun `points scale with completions`() {
        assertEquals(50, Gamification.pointsForCompletionCount(5))
    }

    @Test
    fun `level increases every 100 points`() {
        assertEquals(1, Gamification.levelForPoints(0))
        assertEquals(2, Gamification.levelForPoints(100))
        assertEquals(3, Gamification.levelForPoints(250))
    }

    @Test
    fun `points to next level wraps`() {
        assertEquals(50, Gamification.pointsToNextLevel(150))
    }

    @Test
    fun `badges unlock from stats`() {
        val badge = Gamification.badgesFor(emptyList()).first { it.id == "first" }
        assertTrue(!badge.earned)
    }
}