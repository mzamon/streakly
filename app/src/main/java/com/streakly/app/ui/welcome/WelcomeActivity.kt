package com.streakly.app.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.streakly.app.R
import com.streakly.app.databinding.ActivityWelcomeBinding
import com.streakly.app.ui.auth.LoginActivity
import com.streakly.app.ui.auth.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        b.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        b.btnGoogle.setOnClickListener {
            Toast.makeText(this, R.string.google_sso_stub, Toast.LENGTH_LONG).show()
        }
    }
}
