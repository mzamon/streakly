package com.streakly.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.R
import com.streakly.app.data.repository.AuthRepository
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.databinding.ActivityLoginBinding
import com.streakly.app.ui.main.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = AuthRepository(this)

        binding.tvForgot.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        binding.tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener { attemptLogin() }
    }

    private fun attemptLogin() {
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString()

        if (email.isEmpty()) { showError(getString(R.string.error_email_required)); return }
        if (pass.isEmpty()) { showError(getString(R.string.error_password_required)); return }
        showError(null)

        binding.btnLogin.isEnabled = false
        lifecycleScope.launch {
            val result = auth.login(email, pass)
            binding.btnLogin.isEnabled = true
            when (result) {
                is AuthResult.Success -> {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                is AuthResult.Error -> showError(result.message)
            }
        }
    }

    private fun showError(msg: String?) {
        if (msg == null) {
            binding.tvError.visibility = View.GONE
        } else {
            binding.tvError.text = msg
            binding.tvError.visibility = View.VISIBLE
        }
    }
}