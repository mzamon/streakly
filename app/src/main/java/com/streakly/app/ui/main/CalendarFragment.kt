package com.streakly.app.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.streakly.app.R
import com.streakly.app.data.model.Habit
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.databinding.DialogDayHabitsBinding
import com.streakly.app.databinding.FragmentCalendarBinding
import com.streakly.app.ui.adapters.DayAdapter
import com.streakly.app.ui.adapters.DayCell
import com.streakly.app.ui.adapters.DayState
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var repo: HabitRepository

    private var cursor: YearMonth = YearMonth.now()
    private var habits: List<Habit> = emptyList()
    private var completionMap: Map<Long, Set<String>> = emptyMap()
    private var cells: List<DayCell> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repo = HabitRepository(requireContext())

        binding.btnPrev.setOnClickListener { cursor = cursor.minusMonths(1); refresh() }
        binding.btnNext.setOnClickListener { cursor = cursor.plusMonths(1); refresh() }

        binding.gridDays.setOnItemClickListener { _, _, position, _ ->
            cells.getOrNull(position)?.date?.let { showDaySheet(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        lifecycleScope.launch {
            habits = repo.getHabits()
            completionMap = habits.associate {
                it.localId to repo.getCompletionDates(it.localId).toSet()
            }
            binding.tvMonth.text =
                cursor.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
            cells = buildCells()
            binding.gridDays.adapter = DayAdapter(requireContext(), cells)
        }
    }

    private fun buildCells(): List<DayCell> {
        val result = mutableListOf<DayCell>()
        val first = cursor.atDay(1)
        val today = LocalDate.now()
        repeat(first.dayOfWeek.value - 1) { result.add(DayCell(null, DayState.BLANK)) }

        var d = first
        while (d.month == cursor.month) {
            val due = habits.filter { StreakCalculator.isDueOn(it, d) }
            val state = when {
                d == today -> DayState.TODAY
                due.isEmpty() -> DayState.NOT_DUE
                due.all { completionMap[it.localId]?.contains(d.toString()) == true } -> DayState.DONE
                d.isBefore(today) -> DayState.MISSED
                else -> DayState.NOT_DUE
            }
            result.add(DayCell(d, state))
            d = d.plusDays(1)
        }
        return result
    }

    private fun showDaySheet(date: LocalDate) {
        val dialog = BottomSheetDialog(requireContext())
        val sheet = DialogDayHabitsBinding.inflate(layoutInflater)
        dialog.setContentView(sheet.root)
        sheet.tvDate.text = DateUtils.pretty(date.toString())

        val due = habits.filter { StreakCalculator.isDueOn(it, date) }
        if (due.isEmpty()) {
            val tv = TextView(requireContext())
            tv.text = getString(R.string.legend_not_due)
            tv.setTextColor(requireContext().getColor(R.color.text_muted))
            sheet.containerHabits.addView(tv)
        }
        for (habit in due) {
            val done = completionMap[habit.localId]?.contains(date.toString()) == true
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, 12, 0, 12)
            }
            val name = TextView(requireContext()).apply {
                text = "${habit.icon}  ${habit.name}"
                setTextColor(requireContext().getColor(
                    if (done) R.color.jade_500 else R.color.text_primary))
                layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
            }
            row.addView(name)
            if (!done && !date.isAfter(LocalDate.now())) {
                val btn = TextView(requireContext()).apply {
                    text = getString(R.string.mark_done)
                    setTextColor(requireContext().getColor(R.color.text_on_flame))
                    setBackgroundResource(R.drawable.bg_button_flame)
                    setPadding(28, 12, 28, 12)
                    setOnClickListener {
                        lifecycleScope.launch {
                            repo.markDone(habit, date.toString())
                            dialog.dismiss()
                            refresh()
                        }
                    }
                }
                row.addView(btn)
            }
            sheet.containerHabits.addView(row)
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}