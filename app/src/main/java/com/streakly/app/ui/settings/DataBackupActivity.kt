package com.streakly.app.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.SyncOutcome
import com.streakly.app.databinding.ActivityDataBackupBinding
import com.streakly.app.ui.common.confirm
import com.streakly.app.ui.common.snack
import com.streakly.app.utils.BackupFile
import com.streakly.app.utils.ExportUtils
import com.streakly.app.worker.ReminderScheduler
import com.streakly.app.worker.SyncScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/** Back up to the cloud, export / import data, clear the local cache and delete all data. */
class DataBackupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataBackupBinding

    private val importLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) importFrom(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBackupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.rowBackupNow.setOnClickListener { backupNow() }
        binding.rowExportCsv.setOnClickListener { export(csv = true) }
        binding.rowExportJson.setOnClickListener { export(csv = false) }
        binding.rowImport.setOnClickListener { importLauncher.launch(arrayOf("application/json", "text/plain", "application/octet-stream")) }
        binding.rowClearCache.setOnClickListener {
            confirm(R.string.clear_local_cache, R.string.clear_cache_message, R.string.clear) { clearCache() }
        }
        binding.rowDeleteAll.setOnClickListener {
            confirm(R.string.delete_all_data, R.string.delete_all_message, R.string.delete) {
                lifecycleScope.launch {
                    Graph.habits.deleteAllData()
                    binding.root.snack(R.string.all_data_deleted)
                }
            }
        }
    }

    private fun busy(on: Boolean) {
        binding.progress.visibility = if (on) View.VISIBLE else View.INVISIBLE
    }

    private fun backupNow() {
        lifecycleScope.launch {
            busy(true)
            val message = when (Graph.sync.sync()) {
                is SyncOutcome.Success -> R.string.backup_done
                SyncOutcome.Offline -> R.string.sync_offline
                else -> R.string.sync_failed
            }
            busy(false)
            binding.root.snack(message)
        }
    }

    private fun export(csv: Boolean) {
        lifecycleScope.launch {
            busy(true)
            try {
                val snapshot = Graph.habits.snapshotOnce()
                val text = if (csv) ExportUtils.buildCsv(snapshot.habits, snapshot.logs)
                else ExportUtils.buildJson(
                    BackupFile(exportedAt = System.currentTimeMillis(), habits = snapshot.habits, logs = snapshot.logs,
                        rewards = snapshot.rewards, redemptions = snapshot.redemptions)
                )
                val file = withContext(Dispatchers.IO) {
                    val dir = File(cacheDir, "exports").apply { mkdirs() }
                    File(dir, if (csv) "streakly-habits.csv" else "streakly-backup.json").apply { writeText(text) }
                }
                val uri = FileProvider.getUriForFile(this@DataBackupActivity, "$packageName.fileprovider", file)
                val send = Intent(Intent.ACTION_SEND).apply {
                    type = if (csv) "text/csv" else "application/json"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(Intent.createChooser(send, getString(R.string.export_share_title)))
            } catch (e: Exception) {
                binding.root.snack(R.string.error_export)
            } finally {
                busy(false)
            }
        }
    }

    private fun importFrom(uri: Uri) {
        lifecycleScope.launch {
            busy(true)
            try {
                val text = withContext(Dispatchers.IO) {
                    contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() } ?: ""
                }
                val backup = ExportUtils.parseBackup(text)
                if (backup == null) {
                    binding.root.snack(R.string.error_import_invalid)
                } else {
                    MaterialAlertDialogBuilder(this@DataBackupActivity)
                        .setTitle(R.string.import_backup)
                        .setMessage(getString(R.string.import_confirm, backup.habits.size, backup.logs.size))
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.import_action) { _, _ ->
                            lifecycleScope.launch {
                                val count = Graph.habits.importBackup(backup)
                                ReminderScheduler.rescheduleAll(applicationContext)
                                binding.root.snack(getString(R.string.import_done, count))
                            }
                        }
                        .show()
                }
            } catch (e: Exception) {
                binding.root.snack(R.string.error_import_invalid)
            } finally {
                busy(false)
            }
        }
    }

    /** Uploads anything pending first so no offline work is lost, then re-downloads everything. */
    private fun clearCache() {
        lifecycleScope.launch {
            busy(true)
            val uid = Graph.auth.uid
            if (uid == null) { busy(false); return@launch }
            val outcome = Graph.sync.sync()
            if (outcome is SyncOutcome.Success || Graph.habits.pendingCount() == 0) {
                Graph.habits.clearLocal(uid)
                Graph.rewards.ensureDefaults()
                SyncScheduler.requestNow(applicationContext)
                binding.root.snack(R.string.cache_cleared)
            } else {
                binding.root.snack(R.string.clear_cache_blocked)
            }
            busy(false)
        }
    }
}
