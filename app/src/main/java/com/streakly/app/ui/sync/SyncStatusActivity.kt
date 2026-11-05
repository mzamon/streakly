package com.streakly.app.ui.sync

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.local.entity.ConflictEntity
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity
import com.streakly.app.data.repository.SyncOutcome
import com.streakly.app.data.repository.SyncRepository
import com.streakly.app.databinding.ActivitySyncStatusBinding
import com.streakly.app.databinding.ItemConflictBinding
import com.streakly.app.ui.common.snack
import com.streakly.app.utils.DateUtils
import com.streakly.app.worker.ReminderScheduler
import com.streakly.app.worker.SyncScheduler
import kotlinx.coroutines.launch

/**
 * Conflict resolution: a row edited on two devices while one was offline is shown side by side and the user
 * keeps either the local or the cloud version.
 */
class SyncStatusActivity : AppCompatActivity() {

    private data class Row(val conflict: ConflictEntity, val title: String, val local: String, val cloud: String)

    private lateinit var binding: ActivitySyncStatusBinding
    private var rows: List<Row> = emptyList()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySyncStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun getItemCount(): Int = rows.size
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
                object : RecyclerView.ViewHolder(ItemConflictBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {}
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val b = ItemConflictBinding.bind(holder.itemView)
                val row = rows[position]
                b.tvTitle.text = row.title
                b.tvLocal.text = row.local
                b.tvCloud.text = row.cloud
                b.btnLocal.setOnClickListener { resolve(row, keepLocal = true) }
                b.btnCloud.setOnClickListener { resolve(row, keepLocal = false) }
            }
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        binding.btnSyncNow.setOnClickListener {
            lifecycleScope.launch {
                binding.btnSyncNow.isEnabled = false
                val message = when (Graph.sync.sync()) {
                    is SyncOutcome.Success -> R.string.sync_done_short
                    SyncOutcome.Offline -> R.string.sync_offline
                    else -> R.string.sync_failed
                }
                binding.root.snack(message)
                binding.btnSyncNow.isEnabled = true
            }
        }

        val uid = Graph.auth.uid
        if (uid == null) {
            finish()
            return
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.sync.observeConflicts(uid).collect { conflicts ->
                    rows = conflicts.map { describe(it) }
                    adapter.notifyDataSetChanged()
                    binding.emptyState.visibility = if (rows.isEmpty()) View.VISIBLE else View.GONE
                    binding.tvSummary.text = if (rows.isEmpty()) "" else getString(R.string.conflicts_summary, rows.size)
                }
            }
        }
    }

    private fun resolve(row: Row, keepLocal: Boolean) {
        lifecycleScope.launch {
            if (keepLocal) {
                Graph.sync.resolveKeepLocal(row.conflict.id)
                SyncScheduler.requestNow(applicationContext)
            } else {
                Graph.sync.resolveKeepCloud(row.conflict.id)
                ReminderScheduler.rescheduleAll(applicationContext)
            }
            binding.root.snack(R.string.conflict_resolved)
        }
    }

    private suspend fun describe(conflict: ConflictEntity): Row {
        return try {
            when (conflict.type) {
                SyncRepository.TYPE_HABIT -> {
                    val cloud = gson.fromJson(conflict.serverJson, HabitEntity::class.java)
                    val local = Graph.db.habitDao().getById(conflict.entityId)
                    Row(conflict, getString(R.string.conflict_habit), local?.let { habitText(it) } ?: "-", habitText(cloud))
                }
                SyncRepository.TYPE_LOG -> {
                    val cloud = gson.fromJson(conflict.serverJson, HabitLogEntity::class.java)
                    val local = Graph.db.habitLogDao().getById(conflict.entityId)
                    val habitName = Graph.db.habitDao().getById(cloud.habitId)?.name ?: ""
                    Row(conflict, getString(R.string.conflict_log, habitName), local?.let { logText(it) } ?: "-", logText(cloud))
                }
                SyncRepository.TYPE_REWARD -> {
                    val cloud = gson.fromJson(conflict.serverJson, RewardEntity::class.java)
                    val local = Graph.db.rewardDao().getById(conflict.entityId)
                    Row(conflict, getString(R.string.conflict_reward), local?.let { rewardText(it) } ?: "-", rewardText(cloud))
                }
                else -> {
                    val cloud = gson.fromJson(conflict.serverJson, RedemptionEntity::class.java)
                    val local = Graph.db.redemptionDao().getById(conflict.entityId)
                    Row(conflict, getString(R.string.conflict_redemption), local?.let { redemptionText(it) } ?: "-", redemptionText(cloud))
                }
            }
        } catch (e: Exception) {
            Row(conflict, getString(R.string.conflict_generic), "-", "-")
        }
    }

    private fun habitText(h: HabitEntity): String =
        "${h.icon} ${h.name} · ${if (h.deleted) getString(R.string.deleted) else h.frequencyType.lowercase()}"

    private fun logText(l: HabitLogEntity): String =
        DateUtils.fullDate(l.date) + " · " + getString(if (l.completed) R.string.done else R.string.not_done) +
            (if (l.note.isNotBlank()) " · " + l.note else "")

    private fun rewardText(r: RewardEntity): String =
        "${r.icon} ${r.name} · ${getString(R.string.cost_points, r.cost)}" + (if (r.deleted) " · " + getString(R.string.deleted) else "")

    private fun redemptionText(r: RedemptionEntity): String =
        "${r.rewardName} · ${getString(R.string.cost_points, r.cost)}"
}
