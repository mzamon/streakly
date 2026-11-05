package com.streakly.app.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.streakly.app.Graph
import com.streakly.app.databinding.ActivityWelcomeBinding
import com.streakly.app.ui.auth.AuthFlow
import com.streakly.app.ui.auth.LoginActivity
import com.streakly.app.ui.auth.RegisterActivity
import com.streakly.app.ui.common.SystemBars

/** Entry point for signed-out users: log in, register or continue with Google. */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SystemBars.lightIcons(this, false)

        if (!Graph.auth.isConfigured) binding.tvConfigWarning.visibility = View.VISIBLE

        binding.btnLogin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        binding.btnRegister.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        binding.btnGoogle.setOnClickListener {
            binding.tvError.visibility = View.GONE
            AuthFlow.googleSignIn(
                this,
                onBusy = { busy ->
                    binding.progress.visibility = if (busy) View.VISIBLE else View.INVISIBLE
                    binding.btnGoogle.isEnabled = !busy
                    binding.btnLogin.isEnabled = !busy
                    binding.btnRegister.isEnabled = !busy
                },
                onError = { res ->
                    binding.tvError.setText(res)
                    binding.tvError.visibility = View.VISIBLE
                }
            )
        }
    }
}
