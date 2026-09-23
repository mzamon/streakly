package com.streakly.app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.R
import com.streakly.app.data.repository.AuthRepository
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.repository.RewardRepository
import com.streakly.app.data.session.SessionManager
import com.streakly.app.databinding.ActivitySettingsBinding
import com.streakly.app.ui.welcome.WelcomeActivity
import com.streakly.app.utils.ReminderScheduler
import com.streakly.app.utils.UiUtils
import kotlinx.coroutines.launch
import java.io.File
import java.text.DateFormat
import java.util.Date

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var session: SessionManager
    private lateinit var auth: AuthRepository
    private lateinit var habitRepo: HabitRepository
    private lateinit var rewardRepo: RewardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        auth = AuthRepository(this)
        habitRepo = HabitRepository(this)
        rewardRepo = RewardRepository(this)

        binding.btnBack.setOnClickListener { finish() }
        binding.tvVersion.text = "v1.0"
        binding.tvPersonalValue.text = session.userName ?: ""

        refreshSyncStatus()

        binding.switchNotifications.isChecked = session.notificationsEnabled
        binding.switchNotifications.setOnCheckedChangeListener { _, checked ->
            session.notificationsEnabled = checked
            lifecycleScope.launch {
                ReminderScheduler.rescheduleAll(this@SettingsActivity,
                    habitRepo.getHabits(), checked)
            }
        }

        binding.rowPersonal.setOnClickListener { editName() }
        binding.rowPassword.setOnClickListener { changePassword() }
        binding.rowTheme.setOnClickListener { pickTheme() }
        binding.rowLanguage.setOnClickListener { pickLanguage() }
        binding.rowSyncNow.setOnClickListener { syncNow() }
        binding.rowExport.setOnClickListener { exportCsv() }
        binding.rowDeleteData.setOnClickListener { deleteLocalData() }
        binding.rowAbout.setOnClickListener {
            UiUtils.info(this, getString(R.string.about),
                "Streakly App\n\nVersion 1.0")
        }
        binding.rowDeleteAccount.setOnClickListener { deleteAccount() }
    }

    private fun refreshSyncStatus() {
        binding.tvSyncValue.text = if (session.lastSyncTime == 0L)
            "Never Synced"
        else "Last Synced: " + DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(session.lastSyncTime))
    }

    private fun editName() {
        val input = EditText(this).apply { setText(session.userName ?: "") }
        MaterialAlertDialogBuilder(this)
            .setTitle("Personal Details")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                lifecycleScope.launch {
                    val result = auth.updateName(input.text.toString().trim())
                    if (result is AuthResult.Success) {
                        binding.tvPersonalValue.text = session.userName ?: ""
                        toast("Saved")
                    } else toastMessage((result as AuthResult.Error).message)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun changePassword() {
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 8, 48, 0)
        }
        val current = EditText(this).apply { hint = "Current Password"; inputType = 129 }
        val next = EditText(this).apply { hint = "New Password"; inputType = 129 }
        layout.addView(current); layout.addView(next)
        MaterialAlertDialogBuilder(this)
            .setTitle("Change Password")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                lifecycleScope.launch {
                    val result = auth.changePassword(current.text.toString(), next.text.toString())
                    when (result) {
                        is AuthResult.Success -> toast("Saved")
                        is AuthResult.Error -> toastMessage(result.message)
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun pickTheme() {
        val labels = arrayOf("Dark Theme", "Light Theme", "System Default")
        val values = arrayOf("dark", "light", "system")
        var checked = values.indexOf(session.theme).coerceAtLeast(0)
        MaterialAlertDialogBuilder(this)
            .setTitle("Appearance")
            .setSingleChoiceItems(labels, checked) { _, which -> checked = which }
            .setPositiveButton("OK") { _, _ ->
                session.theme = values[checked]
                binding.tvThemeValue.text = labels[checked]
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
                    when (values[checked]) {
                        "light" -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
                        "system" -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        else -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
                    })
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun pickLanguage() {
        val labels = arrayOf("English", "isiZulu", "Setswana", "System Default")
        val values = arrayOf("en", "zu", "tn", "system")
        var checked = values.indexOf(session.language).coerceAtLeast(0)
        MaterialAlertDialogBuilder(this)
            .setTitle("Language")
            .setSingleChoiceItems(labels, checked) { _, which -> checked = which }
            .setPositiveButton("OK") { _, _ ->
                session.language = values[checked]
                binding.tvLanguageValue.text = labels[checked]
                androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(
                    if (values[checked] == "system")
                        androidx.core.os.LocaleListCompat.getEmptyLocaleList()
                    else androidx.core.os.LocaleListCompat.forLanguageTags(values[checked]))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun syncNow() {
        toast("Syncing...")
        lifecycleScope.launch {
            // Rewards are intentionally local-only; the server contract currently
            // synchronises habits and completion logs.
            val pushed = habitRepo.pushPending()
            val pulled = habitRepo.refreshFromServer()
            if (pushed && pulled) {
                session.lastSyncTime = System.currentTimeMillis()
                refreshSyncStatus()
                toast("Completed")
            } else {
                toastMessage("Network Error")
            }
        }
    }

    private fun exportCsv() {
        lifecycleScope.launch {
            val habits = habitRepo.getHabits()
            val sb = StringBuilder("habit,date_completed\n")
            for (h in habits) {
                for (d in habitRepo.getCompletionDates(h.localId)) {
                    sb.append("\"${h.name.replace("\"", "\"\"")}\",$d\n")
                }
            }
            val dir = File(cacheDir, "exports").apply { mkdirs() }
            val file = File(dir, "streakly_export.csv")
            file.writeText(sb.toString())
            val uri = FileProvider.getUriForFile(this@SettingsActivity,
                packageName + ".provider", file)
            val share = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(share, "Export Data"))
        }
    }

    private fun deleteLocalData() {
        UiUtils.confirm(this, "Delete All Data",
            "Are you sure you want to delete all local data?", R.string.settings) {
            lifecycleScope.launch {
                habitRepo.clearAllLocalData()
                rewardRepo.clearAllLocalData()
                toast("Data Deleted")
            }
        }
    }

    private fun deleteAccount() {
        UiUtils.confirm(this, "Delete Account",
            "Are you sure you want to permanently delete your account?", R.string.settings) {
            lifecycleScope.launch {
                val result = auth.deleteAccount()
                habitRepo.clearAllLocalData()
                rewardRepo.clearAllLocalData()
                if (result is AuthResult.Success) {
                    val intent = Intent(this@SettingsActivity, WelcomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    toastMessage((result as AuthResult.Error).message)
                }
            }
        }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    private fun toastMessage(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}