package com.streakly.app.utils

/** Input validation shared by the screens. Each function returns true when the value is acceptable. */
object Validators {
    private val EMAIL = Regex("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")

    fun isValidEmail(email: String): Boolean = EMAIL.matches(email.trim())

    /** At least 8 characters with an upper-case letter, a lower-case letter and a digit. */
    fun isStrongPassword(password: String): Boolean =
        password.length >= 8 &&
            password.any { it.isUpperCase() } &&
            password.any { it.isLowerCase() } &&
            password.any { it.isDigit() }

    /** 0 = empty/weak, 1 = fair, 2 = good, 3 = strong. */
    fun passwordScore(password: String): Int {
        if (password.isEmpty()) return 0
        var score = 0
        if (password.length >= 8) score++
        if (password.any { it.isUpperCase() } && password.any { it.isLowerCase() } && password.any { it.isDigit() }) score++
        if (password.length >= 12 || password.any { !it.isLetterOrDigit() }) score++
        return score
    }

    fun isValidHabitName(name: String): Boolean = name.trim().length in 1..40

    fun isValidRewardCost(cost: Int?): Boolean = cost != null && cost in 1..10_000

    fun isValidInterval(value: Int?): Boolean = value != null && value in 1..365

    fun isValidPhone(phone: String): Boolean = phone.isBlank() || phone.filter { it.isDigit() }.length in 7..15

    fun isValidHour(hour: Int): Boolean = hour in 0..23
}
