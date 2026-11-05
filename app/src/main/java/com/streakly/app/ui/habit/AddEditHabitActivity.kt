package com.streakly.app.ui.habit

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.databinding.ActivityAddEditHabitBinding
import com.streakly.app.ui.common.clearErrorOnType
import com.streakly.app.ui.common.confirm
import com.streakly.app.ui.common.dp
import com.streakly.app.ui.common.text
import com.streakly.app.utils.ColorUtils
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.Validators
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/** Create or edit a habit: name, icon, colour, schedule (daily / chosen days / every N days) and reminder. */
class AddEditHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditHabitBinding
    private var editing: HabitEntity? = null
    private var icon: String = ColorUtils.HABIT_ICONS[0]
    private var color: String = ColorUtils.HABIT_COLORS[0]
    private var reminderHour = -1
    private var reminderMinute = 0
    private val selectedDays = linkedSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tilName.clearErrorOnType()
        binding.tilInterval.clearErrorOnType()

        buildIconChips()
        buildColorSwatches()
        buildDayChips()

        binding.toggleFrequency.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) updateFrequencyUi(checkedId)
        }
        binding.toggleFrequency.check(R.id.btnDaily)

        binding.rowReminder.onCheckedChange = { on ->
            if (on) pickTime(true) else { reminderHour = -1; updateReminderUi() }
        }
        binding.rowReminderTime.setOnClickListener { pickTime(false) }
        binding.btnSave.setOnClickListener { save() }
        binding.btnDelete.setOnClickListener {
            val habit = editing ?: return@setOnClickListener
            confirm(R.string.delete_habit, R.string.delete_habit_message, R.string.delete) {
                lifecycleScope.launch {
                    Graph.habits.delete(habit.id)
                    finish()
                }
            }
        }

        val id = intent.getStringExtra(EXTRA_ID)
        if (id != null) {
            lifecycleScope.launch {
                val habit = Graph.habits.getHabit(id)
                if (habit == null) finish() else load(habit)
            }
        } else {
            binding.toolbar.setTitle(R.string.new_habit)
            updateReminderUi()
        }
    }

    private fun load(habit: HabitEntity) {
        editing = habit
        binding.toolbar.setTitle(R.string.edit_habit)
        binding.etName.setText(habit.name)
        binding.etDescription.setText(habit.description)
        icon = habit.icon
        color = habit.color
        reminderHour = habit.reminderHour
        reminderMinute = habit.reminderMinute
        selectedDays.clear()
        habit.daysOfWeek.split(",").mapNotNull { it.trim().toIntOrNull() }.forEach { selectedDays.add(it) }
        buildIconChips()
        buildColorSwatches()
        buildDayChips()
        when (habit.frequencyType) {
            "DAYS" -> binding.toggleFrequency.check(R.id.btnDays)
            "INTERVAL" -> {
                binding.etInterval.setText(habit.interval.toString())
                binding.toggleFrequency.check(R.id.btnInterval)
            }
            else -> binding.toggleFrequency.check(R.id.btnDaily)
        }
        binding.btnDelete.visibility = View.VISIBLE
        updateReminderUi()
    }

    private fun buildIconChips() {
        binding.chipIcons.removeAllViews()
        ColorUtils.HABIT_ICONS.forEach { emoji ->
            val chip = layoutInflater.inflate(R.layout.item_chip, binding.chipIcons, false) as Chip
            chip.id = View.generateViewId()
            chip.text = emoji
            chip.textSize = 20f
            chip.isChecked = emoji == icon
            chip.setOnClickListener { icon = emoji }
            binding.chipIcons.addView(chip)
        }
    }

    private fun buildColorSwatches() {
        binding.colorRow.removeAllViews()
        ColorUtils.HABIT_COLORS.forEach { hex ->
            val swatch = FrameLayout(this)
            val lp = LinearLayout.LayoutParams(dp(36), dp(36))
            lp.marginEnd = dp(10)
            swatch.layoutParams = lp
            val bg = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(ColorUtils.parse(hex))
                if (hex == color) setStroke(dp(3), androidx.core.content.ContextCompat.getColor(this@AddEditHabitActivity, R.color.text_primary))
            }
            swatch.background = bg
            swatch.contentDescription = hex
            if (hex == color) {
                val check = ImageView(this)
                check.setImageResource(R.drawable.ic_check)
                androidx.core.widget.ImageViewCompat.setImageTintList(check, android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE))
                check.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply { setMargins(dp(8), dp(8), dp(8), dp(8)) }
                swatch.addView(check)
            }
            swatch.setOnClickListener { color = hex; buildColorSwatches() }
            binding.colorRow.addView(swatch)
        }
    }

    private fun buildDayChips() {
        binding.chipDays.removeAllViews()
        for (iso in 1..7) {
            val chip = layoutInflater.inflate(R.layout.item_chip, binding.chipDays, false) as Chip
            chip.id = View.generateViewId()
            chip.text = DayOfWeek.of(iso).getDisplayName(TextStyle.SHORT, Locale.getDefault())
            chip.isChecked = selectedDays.contains(iso)
            chip.setOnCheckedChangeListener { _, checked -> if (checked) selectedDays.add(iso) else selectedDays.remove(iso) }
            binding.chipDays.addView(chip)
        }
    }

    private fun updateFrequencyUi(checkedId: Int) {
        binding.chipDays.visibility = if (checkedId == R.id.btnDays) View.VISIBLE else View.GONE
        binding.tilInterval.visibility = if (checkedId == R.id.btnInterval) View.VISIBLE else View.GONE
    }

    private fun pickTime(fromSwitch: Boolean) {
        val use24 = DateUtils.is24Hour(this, Graph.settings.timeFormat)
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(if (use24) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .setHour(if (reminderHour >= 0) reminderHour else 8)
            .setMinute(reminderMinute)
            .setTitleText(R.string.reminder_time)
            .build()
        picker.addOnPositiveButtonClickListener {
            reminderHour = picker.hour
            reminderMinute = picker.minute
            updateReminderUi()
        }
        picker.addOnCancelListener { if (fromSwitch && reminderHour < 0) updateReminderUi() }
        picker.addOnNegativeButtonClickListener { if (fromSwitch && reminderHour < 0) updateReminderUi() }
        picker.show(supportFragmentManager, "time")
    }

    private fun updateReminderUi() {
        val on = reminderHour >= 0
        binding.rowReminder.setChecked(on)
        binding.rowReminderTime.visibility = if (on) View.VISIBLE else View.GONE
        binding.dividerReminder.visibility = if (on) View.VISIBLE else View.GONE
        if (on) binding.rowReminderTime.setValue(DateUtils.formatTime(this, reminderHour, reminderMinute, Graph.settings.timeFormat))
    }

    private fun save() {
        binding.tvFormError.visibility = View.GONE
        val name = binding.tilName.text
        val frequency = when (binding.toggleFrequency.checkedButtonId) {
            R.id.btnDays -> "DAYS"
            R.id.btnInterval -> "INTERVAL"
            else -> "DAILY"
        }
        var valid = true
        if (!Validators.isValidHabitName(name)) {
            binding.tilName.error = getString(R.string.error_habit_name)
            valid = false
        }
        var interval = 1
        if (frequency == "INTERVAL") {
            val parsed = binding.tilInterval.text.toIntOrNull()
            if (!Validators.isValidInterval(parsed)) {
                binding.tilInterval.error = getString(R.string.error_interval)
                valid = false
            } else interval = parsed!!
        }
        if (frequency == "DAYS" && selectedDays.isEmpty()) {
            binding.tvFormError.setText(R.string.error_pick_days)
            binding.tvFormError.visibility = View.VISIBLE
            valid = false
        }
        if (!valid) return

        val days = selectedDays.sorted().joinToString(",")
        val existing = editing
        val habit = existing?.copy(
            name = name, description = binding.etDescription.text.toString().trim(), icon = icon, color = color,
            frequencyType = frequency, daysOfWeek = if (frequency == "DAYS") days else "", interval = interval,
            reminderHour = reminderHour, reminderMinute = reminderMinute
        ) ?: HabitEntity(
            id = Graph.habits.newId(), uid = "", name = name,
            description = binding.etDescription.text.toString().trim(), icon = icon, color = color,
            frequencyType = frequency, daysOfWeek = if (frequency == "DAYS") days else "", interval = interval,
            reminderHour = reminderHour, reminderMinute = reminderMinute,
            createdAt = LocalDate.now().toString()
        )
        lifecycleScope.launch {
            Graph.habits.save(habit)
            finish()
        }
    }

    companion object {
        const val EXTRA_ID = "habit_id"
        fun intent(context: Context, habitId: String): Intent =
            Intent(context, AddEditHabitActivity::class.java).putExtra(EXTRA_ID, habitId)
    }
}
