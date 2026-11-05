package com.streakly.app.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.databinding.ActivityEmailVerificationBinding
import com.streakly.app.ui.common.snack
import com.streakly.app.ui.welcome.WelcomeActivity
import kotlinx.coroutines.launch

/** Asks new e-mail/password users to verify their address; they may also continue and verify later. */
class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = Graph.auth.currentUser?.email ?: ""
        binding.tvBody.text = getString(R.string.verify_body, email)

        binding.btnVerified.setOnClickListener { checkVerified() }
        binding.btnContinue.setOnClickListener { goHome() }
        binding.btnResend.setOnClickListener {
            lifecycleScope.launch {
                binding.btnResend.isEnabled = false
                when (val r = Graph.auth.resendVerification()) {
                    is AuthResult.Success -> binding.tvStatus.setText(R.string.verify_sent)
                    is AuthResult.Error -> binding.tvStatus.setText(r.messageRes)
                    is AuthResult.Cancelled -> Unit
                }
                binding.btnResend.isEnabled = true
            }
        }
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                Graph.auth.signOut()
                startActivity(Intent(this@EmailVerificationActivity, WelcomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
            }
        }
    }

    private fun checkVerified() {
        lifecycleScope.launch {
            binding.btnVerified.isEnabled = false
            if (Graph.auth.refreshVerified()) goHome() else binding.tvStatus.setText(R.string.verify_not_yet)
            binding.btnVerified.isEnabled = true
        }
    }

    private fun goHome() {
        startActivity(AuthFlow.homeIntent(this))
        finish()
    }

    override fun onBackPressed() {
        // The verification step is skippable, so back simply continues into the app.
        goHome()
    }
}
