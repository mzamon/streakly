package com.streakly.app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.SyncOutcome
import com.streakly.app.databinding.ActivityOfflineSyncBinding
import com.streakly.app.ui.sync.SyncStatusActivity
import com.streakly.app.utils.NetworkMonitor
import com.streakly.app.worker.ReminderScheduler
import com.streakly.app.worker.SyncScheduler
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date

/** Offline mode status, automatic sync options, manual sync and a link to conflict resolution. */
class OfflineSyncActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfflineSyncBinding
    private var online = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfflineSyncBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        val s = Graph.settings

        binding.rowAutoSync.setChecked(s.autoSync)
        binding.rowAutoSync.onCheckedChange = { on ->
            s.autoSync = on
            SyncScheduler.applyPeriodic(applicationContext)
        }
        binding.rowFrequency.setOnClickListener { chooseFrequency() }
        binding.rowConflicts.setOnClickListener { startActivity(Intent(this, SyncStatusActivity::class.java)) }
        binding.btnSyncNow.setOnClickListener { syncNow() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    NetworkMonitor.observe(applicationContext).collect { isOnline ->
                        online = isOnline
                        renderStatus()
                    }
                }
                launch {
                    Graph.habits.observeSnapshot().collect { refreshCounts() }
                }
            }
        }
        renderFrequency()
    }

    private fun frequencyLabel(minutes: Int): String = when (minutes) {
        15 -> getString(R.string.every_15_min)
        60 -> getString(R.string.every_hour)
        360 -> getString(R.string.every_6_hours)
        else -> getString(R.string.every_30_min)
    }

    private fun renderFrequency() {
        binding.rowFrequency.setValue(frequencyLabel(Graph.settings.syncMinutes))
    }

    private fun chooseFrequency() {
        val values = listOf(15, 30, 60, 360)
        val labels = values.map { frequencyLabel(it) }.toTypedArray()
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.sync_frequency)
            .setSingleChoiceItems(labels, values.indexOf(Graph.settings.syncMinutes).coerceAtLeast(0)) { dialog, which ->
                Graph.settings.syncMinutes = values[which]
                SyncScheduler.applyPeriodic(applicationContext)
                renderFrequency()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun renderStatus() {
        binding.tvStatusTitle.setText(if (online) R.string.status_online else R.string.status_offline)
        binding.ivStatus.setImageResource(if (online) R.drawable.ic_cloud else R.drawable.ic_cloud_off)
        val uid = Graph.auth.uid
        val last = if (uid == null) 0L else Graph.settings.lastSyncAt(uid)
        binding.tvLastSync.text = if (last == 0L) getString(R.string.never_synced)
        else getString(R.string.last_synced, DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Date(last)))
    }

    private fun refreshCounts() {
        lifecycleScope.launch {
            val pending = Graph.habits.pendingCount()
            val conflicts = Graph.sync.conflictCount()
            binding.tvPending.text = pending.toString()
            binding.tvConflicts.text = conflicts.toString()
            binding.rowConflicts.setValue(if (conflicts > 0) conflicts.toString() else null)
            renderStatus()
        }
    }

    private fun syncNow() {
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            binding.btnSyncNow.isEnabled = false
            val message = when (val outcome = Graph.sync.sync()) {
                is SyncOutcome.Success -> {
                    ReminderScheduler.rescheduleAll(applicationContext)
                    getString(R.string.sync_success, outcome.pushed, outcome.pulled)
                }
                SyncOutcome.Offline -> getString(R.string.sync_offline)
                SyncOutcome.NotSignedIn -> getString(R.string.error_generic)
                is SyncOutcome.Failed -> getString(R.string.sync_failed)
            }
            binding.tvSyncResult.text = message
            binding.progress.visibility = View.INVISIBLE
            binding.btnSyncNow.isEnabled = true
            refreshCounts()
        }
    }
}
