package com.streakly.app.ui.habit

import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.R
import com.streakly.app.data.model.Habit
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.session.SessionManager
import com.streakly.app.databinding.ActivityAddEditHabitBinding
import com.streakly.app.utils.ReminderScheduler
import kotlinx.coroutines.launch

class AddEditHabitActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HABIT_ID = "habit_id"
        private val COLORS = listOf("#F2994A", "#3FA796", "#2C6478", "#56CCF2", "#9B51E0", "#F2C94C", "#EB5757", "#6FCF97")
        private val ICONS = listOf("🔥", "💧", "📚", "🏃", "🧘", "🥗", "😴", "✍️")
    }

    private lateinit var binding: ActivityAddEditHabitBinding
    private lateinit var repo: HabitRepository
    private lateinit var session: SessionManager

    private var habitId: Long = -1L
    private var editing: Habit? = null
    private var selectedColor = COLORS[0]
    private var selectedIcon = ICONS[0]
    private var selectedDays = mutableSetOf<Int>()
    private var reminderHour = -1
    private var reminderMinute = 0

    private val dayViews = mutableMapOf<Int, TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repo = HabitRepository(this)
        session = SessionManager(this)

        habitId = intent.getLongExtra(EXTRA_HABIT_ID, -1L)
        binding.btnBack.setOnClickListener { finish() }

        setupFrequency()
        setupDays()
        setupSwatches(binding.rowColors, COLORS, isColor = true)
        setupSwatches(binding.rowIcons, ICONS, isColor = false)
        setupReminder()

        if (habitId != -1L) {
            binding.tvTitle.text = "Edit Habit"
            lifecycleScope.launch {
                editing = repo.getHabit(habitId)
                editing?.let { fill(it) }
            }
        }

        binding.btnSave.setOnClickListener { save() }
    }

    private fun fill(h: Habit) {
        binding.etName.setText(h.name)
        binding.etDescription.setText(h.description)
        binding.etNotes.setText(h.notes)
        binding.etInterval.setText(h.interval.toString())
        selectedColor = h.color; selectedIcon = h.icon
        reminderHour = h.reminderHour; reminderMinute = h.reminderMinute
        selectedDays = h.daysOfWeek.split(",").mapNotNull { it.trim().toIntOrNull() }.toMutableSet()
        when (h.frequencyType) {
            "DAYS" -> binding.tgFrequency.check(R.id.btn_freq_days)
            "INTERVAL" -> binding.tgFrequency.check(R.id.btn_freq_interval)
            else -> binding.tgFrequency.check(R.id.btn_freq_daily)
        }
        updateDayToggles()
        updateReminderLabel()
        highlightSwatches()
    }

    private fun setupFrequency() {
        binding.tgFrequency.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            binding.layoutDays.visibility =
                if (checkedId == R.id.btn_freq_days) View.VISIBLE else View.GONE
            binding.tilInterval.visibility =
                if (checkedId == R.id.btn_freq_interval) View.VISIBLE else View.GONE
        }
        binding.tgFrequency.check(R.id.btn_freq_daily)
    }

    private fun setupDays() {
        val ids = listOf(R.id.day_mon, R.id.day_tue, R.id.day_wed, R.id.day_thu,
                         R.id.day_fri, R.id.day_sat, R.id.day_sun)
        ids.forEachIndexed { index, id ->
            val tv = findViewById<TextView>(id)
            val value = index + 1
            dayViews[value] = tv
            tv.setOnClickListener {
                if (selectedDays.contains(value)) selectedDays.remove(value)
                else selectedDays.add(value)
                updateDayToggles()
            }
        }
    }

    private fun updateDayToggles() {
        for ((value, tv) in dayViews) {
            if (selectedDays.contains(value)) {
                val fill = GradientDrawable()
                fill.shape = GradientDrawable.OVAL
                fill.setColor(getColor(R.color.primary))
                tv.background = fill
                tv.setTextColor(getColor(R.color.text_white))
            } else {
                tv.setBackgroundResource(R.drawable.ic_check)
                tv.setTextColor(getColor(R.color.text_secondary))
            }
        }
    }

    private fun setupSwatches(row: LinearLayout, items: List<String>, isColor: Boolean) {
        for (item in items) {
            val tv = TextView(this).apply {
                if (isColor) {
                    val d = GradientDrawable()
                    d.shape = GradientDrawable.OVAL
                    d.setColor(Color.parseColor(item))
                    background = d
                    text = ""
                } else {
                    text = item
                    textSize = 22f
                    gravity = Gravity.CENTER
                }
                val p = LinearLayout.LayoutParams(72, 72)
                p.setMargins(0, 0, 20, 0)
                layoutParams = p
                setOnClickListener {
                    if (isColor) selectedColor = item else selectedIcon = item
                    highlightSwatches()
                }
            }
            tv.tag = item
            row.addView(tv)
        }
    }

    private fun highlightSwatches() {
        for (row in listOf(binding.rowColors, binding.rowIcons)) {
            for (i in 0 until row.childCount) {
                val v = row.getChildAt(i)
                val selected = v.tag == selectedColor || v.tag == selectedIcon
                v.alpha = if (selected) 1f else 0.45f
            }
        }
    }

    private fun setupReminder() {
        binding.tvReminder.setOnClickListener {
            TimePickerDialog(this, { _, h, m ->
                reminderHour = h; reminderMinute = m
                updateReminderLabel()
            }, if (reminderHour >= 0) reminderHour else 18,
               if (reminderHour >= 0) reminderMinute else 0, true).show()
        }
        binding.btnClearReminder.setOnClickListener {
            reminderHour = -1; reminderMinute = 0
            updateReminderLabel()
        }
    }

    private fun updateReminderLabel() {
        if (reminderHour >= 0) {
            binding.tvReminder.text = String.format("%02d:%02d", reminderHour, reminderMinute)
            binding.btnClearReminder.visibility = View.VISIBLE
        } else {
            binding.tvReminder.text = "Set Reminder"
            binding.btnClearReminder.visibility = View.GONE
        }
    }

    private fun save() {
        val name = binding.etName.text.toString().trim()
        if (name.isEmpty()) {
            binding.tvError.text = "Name required"
            binding.tvError.visibility = View.VISIBLE
            return
        }
        val freqType = when (binding.tgFrequency.checkedButtonId) {
            R.id.btn_freq_days -> "DAYS"
            R.id.btn_freq_interval -> "INTERVAL"
            else -> "DAILY"
        }
        if (freqType == "DAYS" && selectedDays.isEmpty()) {
            binding.tvError.text = "Pick at least one day"
            binding.tvError.visibility = View.VISIBLE
            return
        }

        lifecycleScope.launch {
            val base = editing ?: Habit(name = name)
            val habit = base.copy(
                name = name,
                description = binding.etDescription.text.toString().trim(),
                color = selectedColor,
                icon = selectedIcon,
                frequencyType = freqType,
                daysOfWeek = selectedDays.sorted().joinToString(","),
                interval = binding.etInterval.text.toString().toIntOrNull() ?: 3,
                reminderHour = reminderHour,
                reminderMinute = reminderMinute,
                notes = binding.etNotes.text.toString().trim()
            )
            val savedId = repo.saveHabit(habit)
            if (reminderHour >= 0 && session.notificationsEnabled) {
                ReminderScheduler.schedule(this@AddEditHabitActivity,
                    savedId, reminderHour, reminderMinute)
            } else {
                ReminderScheduler.cancel(this@AddEditHabitActivity, savedId)
            }
            Toast.makeText(this@AddEditHabitActivity, "Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}