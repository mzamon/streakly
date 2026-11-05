package com.streakly.app.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidatorsTest {
    @Test fun `email validation`() {
        assertTrue(Validators.isValidEmail("mzamo@example.com"))
        assertTrue(Validators.isValidEmail("  first.last+tag@mail.co.za "))
        assertFalse(Validators.isValidEmail("mzamo@"))
        assertFalse(Validators.isValidEmail("no-at-sign.com"))
        assertFalse(Validators.isValidEmail(""))
    }

    @Test fun `password strength rules`() {
        assertTrue(Validators.isStrongPassword("Streak123"))
        assertFalse(Validators.isStrongPassword("short1A"))
        assertFalse(Validators.isStrongPassword("alllowercase1"))
        assertFalse(Validators.isStrongPassword("ALLUPPERCASE1"))
        assertFalse(Validators.isStrongPassword("NoDigitsHere"))
    }

    @Test fun `password score`() {
        assertEquals(0, Validators.passwordScore(""))
        assertEquals(1, Validators.passwordScore("abcdefgh"))
        assertEquals(2, Validators.passwordScore("Streak123"))
        assertEquals(3, Validators.passwordScore("Streak123!x"))
    }

    @Test fun `habit and reward limits`() {
        assertTrue(Validators.isValidHabitName("Drink water"))
        assertFalse(Validators.isValidHabitName("   "))
        assertFalse(Validators.isValidHabitName("x".repeat(41)))
        assertTrue(Validators.isValidRewardCost(50))
        assertFalse(Validators.isValidRewardCost(0))
        assertFalse(Validators.isValidRewardCost(null))
        assertFalse(Validators.isValidRewardCost(10_001))
        assertTrue(Validators.isValidInterval(365))
        assertFalse(Validators.isValidInterval(0))
        assertFalse(Validators.isValidInterval(null))
    }

    @Test fun `phone is optional but must look like a number`() {
        assertTrue(Validators.isValidPhone(""))
        assertTrue(Validators.isValidPhone("+27 82 123 4567"))
        assertFalse(Validators.isValidPhone("12"))
    }

    @Test fun `hour must be within a day`() {
        assertTrue(Validators.isValidHour(0))
        assertTrue(Validators.isValidHour(23))
        assertFalse(Validators.isValidHour(-1))
        assertFalse(Validators.isValidHour(24))
    }
}
