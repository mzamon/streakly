package com.streakly.app.ui.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.remote.RetrofitClient
import com.streakly.app.databinding.ActivityNotificationsBinding
import com.streakly.app.push.PushRegistrar
import com.streakly.app.ui.common.snack
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.NetworkMonitor
import com.streakly.app.worker.ReminderScheduler
import kotlinx.coroutines.launch
import java.io.IOException

/** Push, streak-risk alerts, habit reminders and the daily reminder. */
class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) PushRegistrar.enqueue(this) else binding.root.snack(R.string.notifications_blocked)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        val s = Graph.settings

        binding.rowPush.onCheckedChange = { on ->
            s.pushEnabled = on
            if (on) {
                requestPermissionIfNeeded()
                PushRegistrar.enqueue(this)
            }
        }
        binding.rowRisk.onCheckedChange = { on -> s.streakRiskAlerts = on }
        binding.rowHabitReminders.onCheckedChange = { on ->
            s.habitRemindersEnabled = on
            lifecycleScope.launch { ReminderScheduler.rescheduleAll(applicationContext) }
        }
        binding.rowDaily.onCheckedChange = { on ->
            s.dailyReminderEnabled = on
            binding.rowDailyTime.visibility = if (on) View.VISIBLE else View.GONE
            lifecycleScope.launch { ReminderScheduler.rescheduleAll(applicationContext) }
        }
        binding.rowDailyTime.setOnClickListener { pickDailyTime() }
        binding.rowSystem.setOnClickListener {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            try { startActivity(intent) } catch (e: Exception) { binding.root.snack(R.string.error_generic) }
        }
        binding.btnTestPush.setOnClickListener { sendTestPush() }
        refresh()
    }

    private fun requestPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun refresh() {
        val s = Graph.settings
        binding.rowPush.setChecked(s.pushEnabled)
        binding.rowRisk.setChecked(s.streakRiskAlerts)
        binding.rowHabitReminders.setChecked(s.habitRemindersEnabled)
        binding.rowDaily.setChecked(s.dailyReminderEnabled)
        binding.rowDailyTime.visibility = if (s.dailyReminderEnabled) View.VISIBLE else View.GONE
        binding.rowDailyTime.setValue(DateUtils.formatTime(this, s.dailyReminderHour, s.dailyReminderMinute, s.timeFormat))
    }

    private fun pickDailyTime() {
        val s = Graph.settings
        val use24 = DateUtils.is24Hour(this, s.timeFormat)
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(if (use24) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .setHour(s.dailyReminderHour)
            .setMinute(s.dailyReminderMinute)
            .setTitleText(R.string.reminder_time)
            .build()
        picker.addOnPositiveButtonClickListener {
            s.dailyReminderHour = picker.hour
            s.dailyReminderMinute = picker.minute
            refresh()
            lifecycleScope.launch { ReminderScheduler.rescheduleAll(applicationContext) }
        }
        picker.show(supportFragmentManager, "daily_time")
    }

    /** Asks the API to send a real FCM push to this account's devices (also used in the demo video). */
    private fun sendTestPush() {
        if (!NetworkMonitor.isOnline(this)) {
            binding.tvTestResult.setText(R.string.error_offline_action)
            return
        }
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            binding.btnTestPush.isEnabled = false
            binding.tvTestResult.text = ""
            try {
                PushRegistrar.enqueue(applicationContext)
                val response = RetrofitClient.api.testPush()
                binding.tvTestResult.text =
                    if (response.sent > 0) getString(R.string.test_push_sent, response.sent) else getString(R.string.test_push_none)
            } catch (e: IOException) {
                binding.tvTestResult.setText(R.string.error_network)
            } catch (e: Exception) {
                binding.tvTestResult.setText(R.string.error_generic)
            } finally {
                binding.progress.visibility = View.INVISIBLE
                binding.btnTestPush.isEnabled = true
            }
        }
    }
}
