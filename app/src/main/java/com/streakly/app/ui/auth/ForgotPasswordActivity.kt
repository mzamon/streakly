package com.streakly.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.databinding.ActivityForgotPasswordBinding
import com.streakly.app.ui.common.clearErrorOnType
import com.streakly.app.ui.common.text
import com.streakly.app.utils.Validators
import kotlinx.coroutines.launch

/** Sends a Firebase password-reset email. */
class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tilEmail.clearErrorOnType()
        binding.btnBack.setOnClickListener { finish() }
        binding.btnBackToLogin.setOnClickListener { finish() }
        binding.btnSend.setOnClickListener { send() }
    }

    private fun send() {
        binding.tvFormError.visibility = View.GONE
        val email = binding.tilEmail.text
        if (!Validators.isValidEmail(email)) {
            binding.tilEmail.error = getString(R.string.error_email_invalid)
            return
        }
        lifecycleScope.launch {
            setBusy(true)
            when (val result = Graph.auth.sendPasswordReset(email)) {
                is AuthResult.Success -> {
                    binding.formGroup.visibility = View.GONE
                    binding.successGroup.visibility = View.VISIBLE
                    binding.tvSuccessBody.text = getString(R.string.check_email_body, email)
                }
                is AuthResult.Cancelled -> setBusy(false)
                is AuthResult.Error -> {
                    setBusy(false)
                    binding.tvFormError.setText(result.messageRes)
                    binding.tvFormError.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setBusy(busy: Boolean) {
        binding.progress.visibility = if (busy) View.VISIBLE else View.INVISIBLE
        binding.btnSend.isEnabled = !busy
    }
}
