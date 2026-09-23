package com.streakly.app.ui.habit

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.R
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.session.SessionManager
import com.streakly.app.databinding.ActivityHabitDetailBinding
import com.streakly.app.ui.adapters.DayAdapter
import com.streakly.app.ui.adapters.DayCell
import com.streakly.app.ui.adapters.DayState
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.ReminderScheduler
import com.streakly.app.utils.StreakCalculator
import com.streakly.app.utils.UiUtils
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class HabitDetailActivity : AppCompatActivity() {

    companion object { const val EXTRA_HABIT_ID = "habit_id" }

    private lateinit var binding: ActivityHabitDetailBinding
    private lateinit var repo: HabitRepository
    private lateinit var session: SessionManager
    private var habitId: Long = -1L
    private var doneToday = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repo = HabitRepository(this)
        session = SessionManager(this)
        habitId = intent.getLongExtra(EXTRA_HABIT_ID, -1L)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnEdit.setOnClickListener {
            startActivity(android.content.Intent(this, AddEditHabitActivity::class.java)
                .putExtra(AddEditHabitActivity.EXTRA_HABIT_ID, habitId))
        }
        binding.btnDelete.setOnClickListener { confirmDelete() }
        binding.btnSaveNotes.setOnClickListener { saveNotes() }
        binding.btnMarkDone.setOnClickListener {
            lifecycleScope.launch {
                repo.getHabit(habitId)?.let { repo.markDone(it) }
                refresh()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        lifecycleScope.launch {
            val habit = repo.getHabit(habitId) ?: run { finish(); return@launch }
            val dates = repo.getCompletionDates(habitId).toSet()
            doneToday = dates.contains(DateUtils.today())

            binding.tvName.text = "${habit.icon}  ${habit.name}"
            binding.tvCurrentStreak.text =
                StreakCalculator.currentStreak(habit, dates).toString()
            binding.tvBestStreak.text =
                StreakCalculator.bestStreak(habit, dates).toString()
            binding.etNotes.setText(habit.notes)
            binding.tvReminder.text = if (habit.reminderHour >= 0)
                String.format("Reminder %02d:%02d", habit.reminderHour, habit.reminderMinute)
            else ""

            binding.btnMarkDone.isEnabled = !doneToday
            binding.btnMarkDone.alpha = if (doneToday) 0.5f else 1f
            binding.btnMarkDone.setText(if (doneToday) R.string.completed else R.string.mark_done)

            binding.tvReminder.setOnClickListener {
                TimePickerDialog(this@HabitDetailActivity, { _, h, m ->
                    lifecycleScope.launch {
                        val updated = habit.copy(reminderHour = h, reminderMinute = m)
                        repo.saveHabit(updated)
                        if (session.notificationsEnabled) {
                            ReminderScheduler.schedule(this@HabitDetailActivity, habitId, h, m)
                        }
                        refresh()
                    }
                }, habit.reminderHour.coerceAtLeast(0), habit.reminderMinute, true).show()
            }

            binding.gridHeatmap.adapter = DayAdapter(this@HabitDetailActivity, buildCells(habit, dates))
        }
    }

    private fun buildCells(habit: com.streakly.app.data.model.Habit,
                           dates: Set<String>): List<DayCell> {
        val month = YearMonth.now()
        val first = month.atDay(1)
        val today = LocalDate.now()
        val cells = mutableListOf<DayCell>()
        repeat(first.dayOfWeek.value - 1) { cells.add(DayCell(null, DayState.BLANK)) }
        var d = first
        while (d.month == month.month) {
            val due = StreakCalculator.isDueOn(habit, d)
            val completed = dates.contains(d.toString())
            val state = when {
                d == today -> DayState.TODAY
                completed -> DayState.DONE
                due && d.isBefore(today) -> DayState.MISSED
                !due -> DayState.NOT_DUE
                else -> DayState.NOT_DUE
            }
            cells.add(DayCell(d, state))
            d = d.plusDays(1)
        }
        return cells
    }

    private fun saveNotes() {
        lifecycleScope.launch {
            repo.getHabit(habitId)?.let {
                repo.saveHabit(it.copy(notes = binding.etNotes.text.toString()))
            }
            android.widget.Toast.makeText(this@HabitDetailActivity, R.string.save,
                android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDelete() {
        UiUtils.confirm(this, getString(R.string.delete_habit),
            getString(R.string.delete_habit_confirm), R.string.delete) {
            lifecycleScope.launch {
                repo.getHabit(habitId)?.let {
                    repo.deleteHabit(it)
                    ReminderScheduler.cancel(this@HabitDetailActivity, habitId)
                }
                finish()
            }
        }
    }
}