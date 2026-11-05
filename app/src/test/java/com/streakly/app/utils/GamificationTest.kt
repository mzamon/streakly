package com.streakly.app.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GamificationTest {
    @Test fun `ten xp per completion`() {
        assertEquals(0, Gamification.xpForCompletions(0))
        assertEquals(250, Gamification.xpForCompletions(25))
        assertEquals(0, Gamification.xpForCompletions(-4))
    }

    @Test fun `level rises every 100 xp`() {
        assertEquals(1, Gamification.levelForXp(0))
        assertEquals(1, Gamification.levelForXp(99))
        assertEquals(2, Gamification.levelForXp(100))
        assertEquals(4, Gamification.levelForXp(350))
    }

    @Test fun `progress inside the current level`() {
        assertEquals(50, Gamification.xpIntoLevel(350))
        assertEquals(50, Gamification.xpToNextLevel(350))
        assertEquals(100, Gamification.xpToNextLevel(0))
    }

    @Test fun `balance is xp minus spent and never negative`() {
        assertEquals(180, Gamification.balance(230, 50))
        assertEquals(0, Gamification.balance(40, 100))
    }

    @Test fun `can only afford rewards within the balance`() {
        assertTrue(Gamification.canAfford(50, 50))
        assertFalse(Gamification.canAfford(49, 50))
        assertFalse(Gamification.canAfford(500, 0))
    }

    @Test fun `badges unlock at their thresholds`() {
        val none = Gamification.badges(0, 0, 0, 0)
        assertTrue(none.none { it.earned })
        val some = Gamification.badges(totalCompletions = 100, bestStreak = 7, habitCount = 5, redemptions = 1).associateBy { it.id }
        assertTrue(some.getValue("first_flame").earned)
        assertTrue(some.getValue("streak_7").earned)
        assertFalse(some.getValue("streak_30").earned)
        assertTrue(some.getValue("century").earned)
        assertTrue(some.getValue("collector").earned)
        assertTrue(some.getValue("treat_yourself").earned)
    }
}
