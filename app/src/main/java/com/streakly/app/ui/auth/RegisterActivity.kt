package com.streakly.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.streakly.app.R
import com.streakly.app.data.repository.AuthRepository
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.databinding.ActivityRegisterBinding
import com.streakly.app.ui.main.MainActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = AuthRepository(this)

        binding.tvGoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val p = s?.toString() ?: ""
                var score = 0
                if (p.length >= 6) score++
                if (p.length >= 10) score++
                if (p.any { it.isDigit() } && p.any { !it.isLetterOrDigit() }) score++
                binding.pwStrength.progress = score
                binding.pwStrength.progressTintList = ContextCompat.getColorStateList(
                    this@RegisterActivity,
                    when (score) {
                        0 -> R.color.primary_light
                        1 -> R.color.primary
                        else -> R.color.accent
                    }
                )
            }
        })

        binding.btnRegister.setOnClickListener { attemptRegister() }
    }

    private fun attemptRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString()

        if (name.isEmpty()) { showError(getString(R.string.error_name_required)); return }
        if (email.isEmpty()) { showError(getString(R.string.error_email_required)); return }
        if (pass.length < 6) { showError(getString(R.string.error_password_required)); return }
        if (!binding.cbTerms.isChecked) { showError(getString(R.string.error_terms_required)); return }
        showError(null)

        binding.btnRegister.isEnabled = false
        lifecycleScope.launch {
            val result = auth.register(name, email, pass)
            binding.btnRegister.isEnabled = true
            when (result) {
                is AuthResult.Success -> {
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                }
                is AuthResult.Error -> showError(result.message)
            }
        }
    }

    private fun showError(msg: String?) {
        if (msg == null) binding.tvError.visibility = View.GONE
        else {
            binding.tvError.text = msg
            binding.tvError.visibility = View.VISIBLE
        }
    }
}