package com.streakly.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.databinding.ActivityLoginBinding
import com.streakly.app.ui.common.clearErrorOnType
import com.streakly.app.ui.common.text
import com.streakly.app.utils.Validators
import kotlinx.coroutines.launch

/** Validates credentials locally before delegating sign-in and session setup to the auth layer. */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tilEmail.clearErrorOnType()
        binding.tilPassword.clearErrorOnType()
        binding.btnBack.setOnClickListener { finish() }
        binding.btnLogin.setOnClickListener { attemptLogin() }
        binding.tvForgot.setOnClickListener { startActivity(Intent(this, ForgotPasswordActivity::class.java)) }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        binding.btnGoogle.setOnClickListener {
            binding.tvFormError.visibility = View.GONE
            AuthFlow.googleSignIn(this, onBusy = { setBusy(it) }, onError = { showError(it) })
        }
    }

    private fun attemptLogin() {
        binding.tvFormError.visibility = View.GONE
        val email = binding.tilEmail.text
        val password = binding.etPassword.text?.toString() ?: ""
        var valid = true
        if (!Validators.isValidEmail(email)) {
            binding.tilEmail.error = getString(R.string.error_email_invalid)
            valid = false
        }
        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_password_required)
            valid = false
        }
        if (!valid) return

        lifecycleScope.launch {
            setBusy(true)
            when (val result = Graph.auth.login(email, password)) {
                is AuthResult.Success -> {
                    AuthFlow.onSignedIn(applicationContext)
                    startActivity(AuthFlow.homeIntent(this@LoginActivity))
                    finish()
                }
                is AuthResult.Cancelled -> setBusy(false)
                is AuthResult.Error -> {
                    setBusy(false)
                    showError(result.messageRes)
                }
            }
        }
    }

    private fun showError(res: Int) {
        binding.tvFormError.setText(res)
        binding.tvFormError.visibility = View.VISIBLE
    }

    private fun setBusy(busy: Boolean) {
        binding.progress.visibility = if (busy) View.VISIBLE else View.INVISIBLE
        binding.btnLogin.isEnabled = !busy
        binding.btnGoogle.isEnabled = !busy
    }
}
