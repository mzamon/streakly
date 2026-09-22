package com.streakly.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.streakly.app.data.session.SessionManager
import com.streakly.app.ui.main.MainActivity
import com.streakly.app.ui.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val session = SessionManager(this)
            val next = if (session.isLoggedIn) MainActivity::class.java else WelcomeActivity::class.java
            startActivity(Intent(this, next))
            finish()
        }, 800)
    }
}