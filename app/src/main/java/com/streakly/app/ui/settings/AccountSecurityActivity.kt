package com.streakly.app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.databinding.ActivityAccountSecurityBinding
import com.streakly.app.databinding.DialogChangePasswordBinding
import com.streakly.app.databinding.DialogDeleteAccountBinding
import com.streakly.app.ui.common.confirm
import com.streakly.app.ui.common.text
import com.streakly.app.ui.welcome.WelcomeActivity
import com.streakly.app.utils.Validators
import kotlinx.coroutines.launch

/** E-mail verification, change password, link / unlink Google and delete account. */
class AccountSecurityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSecurityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSecurityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.rowVerified.setOnClickListener { resendVerification() }
        binding.rowChangePassword.setOnClickListener { showChangePassword() }
        binding.rowGoogle.setOnClickListener { toggleGoogle() }
        binding.rowDelete.setOnClickListener { showDeleteDialog() }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            Graph.auth.refreshVerified()
            refresh()
        }
    }

    private fun refresh() {
        val auth = Graph.auth
        binding.rowEmail.setValue(auth.currentUser?.email ?: "")
        val verified = auth.currentUser?.isEmailVerified == true
        binding.rowVerified.setValue(getString(if (verified) R.string.verified else R.string.not_verified))
        binding.rowChangePassword.visibility = if (auth.hasPassword) View.VISIBLE else View.GONE
        binding.rowGoogle.setValue(getString(if (auth.hasGoogle) R.string.linked else R.string.not_linked))
    }

    private fun busy(on: Boolean) {
        binding.progress.visibility = if (on) View.VISIBLE else View.INVISIBLE
    }

    private fun show(result: AuthResult, successMessage: Int) {
        binding.tvStatus.setText(
            when (result) {
                is AuthResult.Success -> successMessage
                is AuthResult.Error -> result.messageRes
                is AuthResult.Cancelled -> R.string.cancelled
            }
        )
    }

    private fun resendVerification() {
        if (Graph.auth.currentUser?.isEmailVerified == true) {
            binding.tvStatus.setText(R.string.already_verified)
            return
        }
        lifecycleScope.launch {
            busy(true)
            show(Graph.auth.resendVerification(), R.string.verify_sent)
            busy(false)
        }
    }

    private fun showChangePassword() {
        val d = DialogChangePasswordBinding.inflate(LayoutInflater.from(this))
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.change_password)
            .setView(d.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(android.content.DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val current = d.etCurrent.text?.toString() ?: ""
                val newPw = d.etNew.text?.toString() ?: ""
                val confirm = d.etConfirm.text?.toString() ?: ""
                d.tilCurrent.error = null; d.tilNew.error = null; d.tilConfirm.error = null
                var ok = true
                if (current.isEmpty()) { d.tilCurrent.error = getString(R.string.error_password_required); ok = false }
                if (!Validators.isStrongPassword(newPw)) { d.tilNew.error = getString(R.string.error_password_weak); ok = false }
                if (confirm != newPw) { d.tilConfirm.error = getString(R.string.error_password_mismatch); ok = false }
                if (!ok) return@setOnClickListener
                dialog.dismiss()
                lifecycleScope.launch {
                    busy(true)
                    show(Graph.auth.changePassword(current, newPw), R.string.password_changed)
                    busy(false)
                }
            }
        }
        dialog.show()
    }

    private fun toggleGoogle() {
        val auth = Graph.auth
        if (auth.hasGoogle) {
            confirm(R.string.unlink_google, R.string.unlink_google_message, R.string.unlink) {
                lifecycleScope.launch {
                    busy(true)
                    show(auth.unlinkGoogle(), R.string.google_unlinked)
                    busy(false)
                    refresh()
                }
            }
        } else {
            lifecycleScope.launch {
                busy(true)
                show(auth.linkGoogle(this@AccountSecurityActivity), R.string.google_linked)
                busy(false)
                refresh()
            }
        }
    }

    private fun showDeleteDialog() {
        val d = DialogDeleteAccountBinding.inflate(LayoutInflater.from(this))
        // Google-only accounts re-authenticate through the Google account chooser instead of a password.
        d.tilPassword.visibility = if (Graph.auth.hasPassword) View.VISIBLE else View.GONE
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.delete_account)
            .setView(d.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.delete_account, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(android.content.DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val password = d.etPassword.text?.toString() ?: ""
                if (Graph.auth.hasPassword && password.isEmpty()) {
                    d.tilPassword.error = getString(R.string.error_password_required)
                    return@setOnClickListener
                }
                dialog.dismiss()
                deleteAccount(password)
            }
        }
        dialog.show()
    }

    private fun deleteAccount(password: String) {
        lifecycleScope.launch {
            busy(true)
            val uid = Graph.auth.uid
            when (val result = Graph.auth.deleteAccount(password, this@AccountSecurityActivity)) {
                is AuthResult.Success -> {
                    if (uid != null) Graph.habits.clearLocal(uid)
                    androidx.work.WorkManager.getInstance(applicationContext).cancelAllWork()
                    startActivity(Intent(this@AccountSecurityActivity, WelcomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finishAffinity()
                }
                is AuthResult.Error -> { busy(false); binding.tvStatus.setText(result.messageRes) }
                is AuthResult.Cancelled -> busy(false)
            }
        }
    }
}
