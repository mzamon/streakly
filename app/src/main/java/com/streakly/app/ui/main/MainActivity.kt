package com.streakly.app.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.databinding.ActivityMainBinding
import com.streakly.app.push.AppIntents
import com.streakly.app.push.PushRegistrar
import com.streakly.app.ui.habit.HabitDetailActivity
import com.streakly.app.ui.splash.SplashActivity
import com.streakly.app.ui.sync.SyncStatusActivity
import com.streakly.app.utils.NetworkMonitor
import com.streakly.app.worker.SyncScheduler
import kotlinx.coroutines.launch

/** Hosts the four bottom-navigation tabs and routes notification taps to the right screen. */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.d(TAG, "Notification permission granted=$granted")
            if (granted) PushRegistrar.enqueue(this)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Graph.auth.isSignedIn) {
            startActivity(Intent(this, SplashActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
            return
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            show(item.itemId)
            true
        }
        if (savedInstanceState == null) binding.bottomNav.selectedItemId = R.id.nav_home

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var wasOffline = false
                NetworkMonitor.observe(applicationContext).collect { online ->
                    binding.bannerOffline.visibility = if (online) View.GONE else View.VISIBLE
                    if (online && wasOffline) SyncScheduler.requestNow(applicationContext)
                    wasOffline = !online
                }
            }
        }

        SyncScheduler.requestNow(this)
        maybeAskForNotifications()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (::binding.isInitialized) handleIntent(intent)
    }

    private fun show(itemId: Int) {
        val fragment: Fragment = when (itemId) {
            R.id.nav_calendar -> CalendarFragment()
            R.id.nav_rewards -> com.streakly.app.ui.rewards.RewardsFragment()
            R.id.nav_profile -> ProfileFragment()
            else -> HomeFragment()
        }
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.container, fragment)
            .commit()
    }

    /** Notification deep links: STREAK_RISK/reminder -> habit, reward -> Rewards tab, conflict -> Sync status. */
    private fun handleIntent(intent: Intent?) {
        val dest = intent?.getStringExtra(AppIntents.EXTRA_DEST) ?: return
        val habitId = intent.getStringExtra(AppIntents.EXTRA_HABIT_ID)
        intent.removeExtra(AppIntents.EXTRA_DEST)
        intent.removeExtra(AppIntents.EXTRA_HABIT_ID)
        when (dest) {
            AppIntents.DEST_HABIT -> if (habitId != null) startActivity(HabitDetailActivity.intent(this, habitId))
            AppIntents.DEST_REWARDS -> binding.bottomNav.selectedItemId = R.id.nav_rewards
            AppIntents.DEST_SYNC -> startActivity(Intent(this, SyncStatusActivity::class.java))
            else -> binding.bottomNav.selectedItemId = R.id.nav_home
        }
    }

    private fun maybeAskForNotifications() {
        if (Build.VERSION.SDK_INT < 33) return
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) return
        if (Graph.settings.notificationPromptShown) return
        Graph.settings.notificationPromptShown = true
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.notif_prompt_title)
            .setMessage(R.string.notif_prompt_body)
            .setNegativeButton(R.string.not_now, null)
            .setPositiveButton(R.string.allow) { _, _ -> notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS) }
            .show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
