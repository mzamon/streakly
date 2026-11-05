package com.streakly.app.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.databinding.ActivitySettingsBinding
import com.streakly.app.ui.common.confirm
import com.streakly.app.ui.welcome.WelcomeActivity
import kotlinx.coroutines.launch

/** Root of the settings hierarchy. */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        fun open(cls: Class<*>) = startActivity(Intent(this, cls))
        binding.rowPersonal.setOnClickListener { open(PersonalDetailsActivity::class.java) }
        binding.rowSecurity.setOnClickListener { open(AccountSecurityActivity::class.java) }
        binding.rowNotifications.setOnClickListener { open(NotificationsActivity::class.java) }
        binding.rowPreferences.setOnClickListener { open(PreferencesActivity::class.java) }
        binding.rowSync.setOnClickListener { open(OfflineSyncActivity::class.java) }
        binding.rowBackup.setOnClickListener { open(DataBackupActivity::class.java) }
        binding.rowAbout.setOnClickListener { open(AboutActivity::class.java) }
        binding.rowLogout.setOnClickListener {
            confirm(R.string.logout, R.string.logout_message, R.string.logout) {
                lifecycleScope.launch {
                    Graph.auth.signOut()
                    startActivity(Intent(this@SettingsActivity, WelcomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finishAffinity()
                }
            }
        }
    }
}
