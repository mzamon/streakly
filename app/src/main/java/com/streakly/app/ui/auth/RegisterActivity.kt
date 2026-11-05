package com.streakly.app.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.BuildConfig
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.databinding.ActivityRegisterBinding
import com.streakly.app.ui.common.clearErrorOnType
import com.streakly.app.ui.common.snack
import com.streakly.app.ui.common.text
import com.streakly.app.utils.Validators
import kotlinx.coroutines.launch

/** Handles account creation, password-strength feedback, terms acknowledgement, and Google sign-in. */
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listOf(binding.tilName, binding.tilEmail, binding.tilPassword, binding.tilConfirm).forEach { it.clearErrorOnType() }
        binding.btnBack.setOnClickListener { finish() }
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateStrength(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        updateStrength("")
        binding.tvReadTerms.setOnClickListener { showTerms() }
        binding.btnRegister.setOnClickListener { attemptRegister() }
        binding.btnGoogle.setOnClickListener {
            binding.tvFormError.visibility = View.GONE
            AuthFlow.googleSignIn(this, onBusy = { setBusy(it) }, onError = { showError(it) })
        }
    }

    private fun updateStrength(password: String) {
        val score = Validators.passwordScore(password)
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.danger),
            ContextCompat.getColor(this, R.color.warning),
            ContextCompat.getColor(this, R.color.teal)
        )
        val off = ContextCompat.getColor(this, R.color.card_elevated)
        val segs = listOf(binding.seg1, binding.seg2, binding.seg3)
        segs.forEachIndexed { index, view ->
            val on = score > index
            ViewCompat.setBackgroundTintList(view, android.content.res.ColorStateList.valueOf(if (on) colors[(score - 1).coerceIn(0, 2)] else off))
        }
        binding.tvStrength.setText(
            when (score) {
                0 -> R.string.strength_none
                1 -> R.string.strength_fair
                2 -> R.string.strength_good
                else -> R.string.strength_strong
            }
        )
        if (password.isEmpty()) binding.tvStrength.text = ""
    }

    private fun attemptRegister() {
        binding.tvFormError.visibility = View.GONE
        val name = binding.tilName.text
        val email = binding.tilEmail.text
        val password = binding.etPassword.text?.toString() ?: ""
        val confirm = binding.etConfirm.text?.toString() ?: ""
        var valid = true
        if (name.isBlank()) { binding.tilName.error = getString(R.string.error_name_required); valid = false }
        if (!Validators.isValidEmail(email)) { binding.tilEmail.error = getString(R.string.error_email_invalid); valid = false }
        if (!Validators.isStrongPassword(password)) { binding.tilPassword.error = getString(R.string.error_password_weak); valid = false }
        if (confirm != password) { binding.tilConfirm.error = getString(R.string.error_password_mismatch); valid = false }
        if (!binding.cbTerms.isChecked) { binding.root.snack(R.string.error_terms_required); valid = false }
        if (!valid) return

        lifecycleScope.launch {
            setBusy(true)
            when (val result = Graph.auth.register(name, email, password)) {
                is AuthResult.Success -> {
                    AuthFlow.onSignedIn(applicationContext)
                    startActivity(AuthFlow.verifyIntent(this@RegisterActivity))
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

    private fun showTerms() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.terms_title)
            .setMessage(R.string.terms_body)
            .setNegativeButton(R.string.close, null)
            .setPositiveButton(R.string.privacy_policy) { _, _ ->
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_URL)))
                } catch (e: Exception) {
                    binding.root.snack(R.string.error_generic)
                }
            }
            .show()
    }

    private fun showError(res: Int) {
        binding.tvFormError.setText(res)
        binding.tvFormError.visibility = View.VISIBLE
    }

    private fun setBusy(busy: Boolean) {
        binding.progress.visibility = if (busy) View.VISIBLE else View.INVISIBLE
        binding.btnRegister.isEnabled = !busy
        binding.btnGoogle.isEnabled = !busy
    }
}
