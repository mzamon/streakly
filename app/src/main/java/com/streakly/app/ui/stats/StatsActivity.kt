package com.streakly.app.ui.stats

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.model.AppSnapshot
import com.streakly.app.data.model.schedule
import com.streakly.app.databinding.ActivityStatsBinding
import com.streakly.app.databinding.ItemHabitRateBinding
import com.streakly.app.utils.ColorUtils
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.StatsCalculator
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

/** "My Progress": completion ring, bar chart for the week / month / year, key stats and a per-habit breakdown. */
class StatsActivity : AppCompatActivity() {

    private enum class Period { WEEK, MONTH, YEAR }

    private lateinit var binding: ActivityStatsBinding
    private var period = Period.WEEK
    private var snapshot: AppSnapshot = AppSnapshot.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.chipPeriod.setOnCheckedStateChangeListener { _, checkedIds ->
            period = when (checkedIds.firstOrNull()) {
                R.id.chipMonth -> Period.MONTH
                R.id.chipYear -> Period.YEAR
                else -> Period.WEEK
            }
            render()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { snapshot = it; render() }
            }
        }
    }

    private fun render() {
        val today = LocalDate.now()
        val schedules = snapshot.habits.associate { it.id to it.schedule() }
        val done = snapshot.completions

        val values = ArrayList<Float>()
        val labels = ArrayList<String>()
        var highlight = -1
        val rangeStart: LocalDate
        val rangeEnd: LocalDate

        when (period) {
            Period.WEEK -> {
                rangeStart = DateUtils.startOfWeek(today, Graph.settings.weekStart)
                rangeEnd = rangeStart.plusDays(6)
                val names = DateUtils.weekdayLabels(Graph.settings.weekStart)
                for (i in 0..6) {
                    val d = rangeStart.plusDays(i.toLong())
                    values.add(StatsCalculator.counts(schedules, done, d, d, today).percent.toFloat())
                    labels.add(names[i].take(3))
                    if (d == today) highlight = i
                }
            }
            Period.MONTH -> {
                rangeStart = today.withDayOfMonth(1)
                rangeEnd = today.withDayOfMonth(today.lengthOfMonth())
                var cursor = rangeStart
                var index = 0
                while (!cursor.isAfter(rangeEnd)) {
                    val chunkEnd = minOf(cursor.plusDays(6), rangeEnd)
                    values.add(StatsCalculator.counts(schedules, done, cursor, chunkEnd, today).percent.toFloat())
                    labels.add(getString(R.string.week_short, index + 1))
                    if (!today.isBefore(cursor) && !today.isAfter(chunkEnd)) highlight = index
                    cursor = chunkEnd.plusDays(1)
                    index++
                }
            }
            Period.YEAR -> {
                rangeStart = LocalDate.of(today.year, 1, 1)
                rangeEnd = LocalDate.of(today.year, 12, 31)
                for (m in 1..12) {
                    val start = LocalDate.of(today.year, m, 1)
                    val end = start.withDayOfMonth(start.lengthOfMonth())
                    values.add(StatsCalculator.counts(schedules, done, start, end, today).percent.toFloat())
                    labels.add(Month.of(m).getDisplayName(TextStyle.SHORT, Locale.getDefault()).take(3))
                    if (m == today.monthValue) highlight = m - 1
                }
            }
        }
        binding.barChart.setData(values, labels, highlight)

        val overall = StatsCalculator.counts(schedules, done, rangeStart, rangeEnd, today)
        binding.tvPercent.text = "${overall.percent}%"
        binding.ring.animateTo(overall.percent.toFloat())

        binding.statCurrent.set(getString(R.string.current_streak), resources.getQuantityString(R.plurals.days_count, snapshot.currentStreak(today), snapshot.currentStreak(today)))
        binding.statBest.set(getString(R.string.best_streak), resources.getQuantityString(R.plurals.days_count, snapshot.bestStreak(today), snapshot.bestStreak(today)))
        binding.statDone.set(getString(R.string.completed_in_period), overall.done.toString())
        binding.statTotal.set(getString(R.string.total_completions), snapshot.totalCompletions.toString())

        // Per-habit breakdown
        binding.habitRates.removeAllViews()
        snapshot.habits.forEach { habit ->
            val counts = StatsCalculator.counts(mapOf(habit.id to habit.schedule()), done, rangeStart, rangeEnd, today)
            val row = ItemHabitRateBinding.inflate(LayoutInflater.from(this), binding.habitRates, false)
            row.tvIcon.setImageResource(ColorUtils.iconResource(this, habit.icon))
            row.tvIcon.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(ColorUtils.withAlpha(ColorUtils.parse(habit.color), 48))
            }
            row.tvName.text = habit.name
            row.progress.setProgressCompat(counts.percent, false)
            row.tvPercent.text = "${counts.percent}%"
            binding.habitRates.addView(row.root)
        }
        binding.tvNoData.visibility = if (snapshot.habits.isEmpty()) View.VISIBLE else View.GONE
    }
}
