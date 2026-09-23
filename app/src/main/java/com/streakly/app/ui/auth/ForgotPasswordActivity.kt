package com.streakly.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.R
import com.streakly.app.data.repository.AuthRepository
import com.streakly.app.databinding.ActivityForgotBinding
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnBackToLogin.setOnClickListener { finish() }
        
        binding.btnReset.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isEmpty()) {
                binding.etEmail.error = "Email required"
                return@setOnClickListener
            }
            binding.btnReset.isEnabled = false
            lifecycleScope.launch {
                AuthRepository(this@ForgotPasswordActivity).forgotPassword(email)
                // Always show the success state (prevents account enumeration)
                binding.layoutForm.visibility = View.GONE
                binding.layoutSuccess.visibility = View.VISIBLE
            }
        }
    }
}