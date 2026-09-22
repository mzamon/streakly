package com.streakly.app.data.repository

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}
