package com.streakly.app.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.streakly.app.R
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.session.SessionManager
import com.streakly.app.databinding.ActivityMainBinding
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.NotificationHelper
import com.streakly.app.utils.ReminderScheduler
import com.streakly.app.utils.StreakCalculator
import com.streakly.app.worker.SyncWorker
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> showFragment(HomeFragment())
                R.id.nav_calendar -> showFragment(CalendarFragment())
                R.id.nav_rewards -> showFragment(RewardsFragment())
                R.id.nav_profile -> showFragment(ProfileFragment())
            }
            true
        }
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.nav_home
        }

        requestNotificationPermission()
        SyncWorker.schedulePeriodic(this)

        lifecycleScope.launch {
            val repo = HabitRepository(this@MainActivity)
            val session = SessionManager(this@MainActivity)
            val habits = repo.getHabits()
            ReminderScheduler.rescheduleAll(this@MainActivity, habits, session.notificationsEnabled)
            checkStreakRisk(repo, habits, session)
        }
    }

    fun navigateTo(tabId: Int) {
        binding.bottomNav.selectedItemId = tabId
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private suspend fun checkStreakRisk(
        repo: HabitRepository,
        habits: List<com.streakly.app.data.model.Habit>,
        session: SessionManager
    ) {
        val today = DateUtils.today()
        if (session.lastRiskAlertDate == today) return
        for (habit in habits) {
            val dates = repo.getCompletionDates(habit.localId).toSet()
            if (StreakCalculator.brokeStreakYesterday(habit, dates)) {
                if (session.notificationsEnabled) {
                    NotificationHelper.showStreakRisk(this, habit.name)
                }
                session.lastRiskAlertDate = today
                break
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }
    }
}