package com.streakly.app.ui.habit

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.model.AppSnapshot
import com.streakly.app.data.model.schedule
import com.streakly.app.databinding.ActivityHabitDetailBinding
import com.streakly.app.ui.adapters.DayAdapter
import com.streakly.app.ui.adapters.DayCell
import com.streakly.app.ui.adapters.DayState
import com.streakly.app.ui.common.confirm
import com.streakly.app.ui.common.snack
import com.streakly.app.utils.ColorUtils
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/** One habit in depth: streak stats, weekly progress, a month heat-map, a note per day and the reminder. */
class HabitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHabitDetailBinding
    private lateinit var adapter: DayAdapter
    private lateinit var habitId: String
    private var habit: HabitEntity? = null
    private var snapshot: AppSnapshot = AppSnapshot.EMPTY
    private var month: YearMonth = YearMonth.now()
    private var selected: LocalDate = LocalDate.now()
    private var noteDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        habitId = intent.getStringExtra(EXTRA_ID) ?: run { finish(); return }

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> { startActivity(AddEditHabitActivity.intent(this, habitId)); true }
                R.id.action_delete -> { confirmDelete(); true }
                else -> false
            }
        }

        adapter = DayAdapter { date ->
            selected = date
            noteDate = null
            render()
        }
        binding.rvDays.layoutManager = GridLayoutManager(this, 7)
        binding.rvDays.adapter = adapter
        binding.btnPrev.setOnClickListener { month = month.minusMonths(1); render() }
        binding.btnNext.setOnClickListener { month = month.plusMonths(1); render() }
        binding.btnComplete.setOnClickListener { toggleSelected() }
        binding.btnSaveNote.setOnClickListener { saveNote() }
        binding.rowReminder.setOnClickListener { reminderClicked() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { snap ->
                    snapshot = snap
                    val found = snap.habits.firstOrNull { it.id == habitId }
                    if (found == null) finish() else { habit = found; render() }
                }
            }
        }
    }

    private fun confirmDelete() {
        confirm(R.string.delete_habit, R.string.delete_habit_message, R.string.delete) {
            lifecycleScope.launch {
                Graph.habits.delete(habitId)
                finish()
            }
        }
    }

    private fun isDue(h: HabitEntity, date: LocalDate): Boolean =
        StreakCalculator.isDueOn(h.schedule(), date, LocalDate.now())

    private fun toggleSelected() {
        val h = habit ?: return
        val today = LocalDate.now()
        when {
            selected.isAfter(today) -> binding.root.snack(R.string.future_day_hint)
            !isDue(h, selected) -> binding.root.snack(R.string.not_due_that_day)
            else -> lifecycleScope.launch {
                Graph.habits.setCompleted(h.id, selected.toString(), !snapshot.isDone(h.id, selected))
            }
        }
    }

    private fun saveNote() {
        val note = binding.etNote.text?.toString() ?: ""
        lifecycleScope.launch {
            Graph.habits.saveNote(habitId, selected.toString(), note)
            binding.root.snack(R.string.note_saved)
        }
    }

    private fun reminderClicked() {
        val h = habit ?: return
        if (h.reminderHour < 0) {
            pickTime(h)
            return
        }
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.habit_reminder)
            .setItems(arrayOf(getString(R.string.change_time), getString(R.string.remove_reminder))) { _, which ->
                if (which == 0) pickTime(h) else lifecycleScope.launch {
                    Graph.habits.save(h.copy(reminderHour = -1, reminderMinute = 0))
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun pickTime(h: HabitEntity) {
        val use24 = DateUtils.is24Hour(this, Graph.settings.timeFormat)
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(if (use24) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .setHour(if (h.reminderHour >= 0) h.reminderHour else 8)
            .setMinute(h.reminderMinute)
            .setTitleText(R.string.reminder_time)
            .build()
        picker.addOnPositiveButtonClickListener {
            lifecycleScope.launch { Graph.habits.save(h.copy(reminderHour = picker.hour, reminderMinute = picker.minute)) }
        }
        picker.show(supportFragmentManager, "time")
    }

    private fun scheduleText(h: HabitEntity): String = when (h.frequencyType) {
        "DAYS" -> h.daysOfWeek.split(",").mapNotNull { it.trim().toIntOrNull() }.sorted().joinToString(", ") {
            DayOfWeek.of(it.coerceIn(1, 7)).getDisplayName(TextStyle.SHORT, Locale.getDefault())
        }
        "INTERVAL" -> resources.getQuantityString(R.plurals.every_n_days, h.interval, h.interval)
        else -> getString(R.string.every_day)
    }

    private fun render() {
        val h = habit ?: return
        val today = LocalDate.now()
        val settings = Graph.settings

        binding.tvIcon.text = h.icon
        binding.tvIcon.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(ColorUtils.withAlpha(ColorUtils.parse(h.color), 56))
        }
        binding.tvName.text = h.name
        binding.tvDescription.text = h.description
        binding.tvDescription.visibility = if (h.description.isBlank()) View.GONE else View.VISIBLE
        binding.tvSchedule.text = scheduleText(h)

        val stats = snapshot.statsFor(h, today)
        binding.statCurrent.set(getString(R.string.current_streak), resources.getQuantityString(R.plurals.days_count, stats.currentStreak, stats.currentStreak))
        binding.statBest.set(getString(R.string.longest_streak), resources.getQuantityString(R.plurals.days_count, stats.bestStreak, stats.bestStreak))
        binding.statRate.set(getString(R.string.completion_rate), "${stats.completionRate}%")
        binding.statTotal.set(getString(R.string.total_completions), stats.totalCompletions.toString())

        val weekStart = DateUtils.startOfWeek(today, settings.weekStart)
        val weekRate = StreakCalculator.completionRate(h.schedule(), snapshot.doneDates(h.id), weekStart, weekStart.plusDays(6), today)
        binding.progressWeek.setProgressCompat(weekRate, true)
        binding.tvWeekPercent.text = "$weekRate%"
        binding.tvWeekLabel.setText(R.string.this_week)

        // Month grid (week-day header + day cells)
        binding.tvMonth.text = DateUtils.monthTitle(month)
        binding.weekdayRow.removeAllViews()
        DateUtils.weekdayLabels(settings.weekStart).forEach { label ->
            val tv = TextView(this)
            tv.layoutParams = android.widget.LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            tv.gravity = Gravity.CENTER
            tv.text = label
            tv.textSize = 12f
            tv.setTextColor(ContextCompat.getColor(this, R.color.text_muted))
            binding.weekdayRow.addView(tv)
        }
        val cells = ArrayList<DayCell>()
        repeat(DateUtils.leadingBlanks(month, settings.weekStart)) { cells.add(DayCell(null, DayState.NONE, false, false)) }
        for (day in 1..month.lengthOfMonth()) {
            val date = month.atDay(day)
            val due = isDue(h, date)
            val done = snapshot.isDone(h.id, date)
            val state = when {
                done -> DayState.DONE
                !due -> DayState.NONE
                date.isAfter(today) -> DayState.UPCOMING
                date.isBefore(today) -> DayState.MISSED
                else -> DayState.UPCOMING
            }
            cells.add(DayCell(date, state, date == today, date == selected))
        }
        adapter.submit(cells)

        // Note for the selected day (only reload the text when the day changes, so typing is not overwritten)
        binding.tvNoteLabel.text = getString(R.string.note_for, DateUtils.fullDate(selected))
        if (noteDate != selected) {
            noteDate = selected
            val log = snapshot.logs.firstOrNull { it.id == HabitLogEntity.idFor(h.id, selected.toString()) }
            binding.etNote.setText(log?.note ?: "")
        }

        val doneSelected = snapshot.isDone(h.id, selected)
        binding.btnComplete.setText(if (doneSelected) R.string.mark_incomplete else R.string.mark_complete)
        binding.rowReminder.setValue(
            if (h.reminderHour >= 0) DateUtils.formatTime(this, h.reminderHour, h.reminderMinute, settings.timeFormat)
            else getString(R.string.off)
        )
    }

    companion object {
        const val EXTRA_ID = "habit_id"
        fun intent(context: Context, habitId: String): Intent =
            Intent(context, HabitDetailActivity::class.java).putExtra(EXTRA_ID, habitId)
    }
}
