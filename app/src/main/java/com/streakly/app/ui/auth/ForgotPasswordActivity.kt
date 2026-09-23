package com.streakly.app.ui.auth

import android.os.Bundle
import android.widget.EditText
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.R
import com.streakly.app.data.repository.AuthRepository
import com.streakly.app.data.repository.AuthResult
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
            val newPassword = EditText(this).apply {
                hint = "New password"
                inputType = 129
            }
            MaterialAlertDialogBuilder(this)
                .setTitle("Choose a new password")
                .setView(newPassword)
                .setNegativeButton("Cancel") { _, _ -> binding.btnReset.isEnabled = true }
                .setPositiveButton("Reset") { _, _ ->
                    lifecycleScope.launch {
                        val result = AuthRepository(this@ForgotPasswordActivity)
                            .resetPassword(email, newPassword.text.toString())
                        binding.btnReset.isEnabled = true
                        when (result) {
                            is AuthResult.Success -> {
                                binding.layoutForm.visibility = View.GONE
                                binding.layoutSuccess.visibility = View.VISIBLE
                            }
                            is AuthResult.Error -> {
                                newPassword.error = result.message
                            }
                        }
                    }
                }
                .setOnCancelListener { binding.btnReset.isEnabled = true }
                .show()
        }
    }
}