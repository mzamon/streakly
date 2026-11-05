package com.streakly.app.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.model.AppSnapshot
import com.streakly.app.data.model.schedule
import com.streakly.app.databinding.FragmentCalendarBinding
import com.streakly.app.ui.adapters.DayAdapter
import com.streakly.app.ui.adapters.DayCell
import com.streakly.app.ui.adapters.DayState
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

/** Month grid with a coloured dot per day; tap a day to complete habits or add notes in a bottom sheet. */
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DayAdapter
    private var month: YearMonth = YearMonth.now()
    private var selected: LocalDate = LocalDate.now()
    private var filterHabitId: String? = null
    private var latest: AppSnapshot = AppSnapshot.EMPTY
    private var chipSignature = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DayAdapter { date ->
            selected = date
            render()
            DayHabitsSheet.newInstance(date.toString()).show(childFragmentManager, "day")
        }
        binding.rvDays.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.rvDays.adapter = adapter

        binding.btnPrev.setOnClickListener { month = month.minusMonths(1); render() }
        binding.btnNext.setOnClickListener { month = month.plusMonths(1); render() }
        binding.btnToday.setOnClickListener { month = YearMonth.now(); selected = LocalDate.now(); render() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect {
                    latest = it
                    rebuildChips()
                    render()
                }
            }
        }
    }

    private fun rebuildChips() {
        val b = _binding ?: return
        val signature = latest.habits.joinToString("|") { it.id + it.name } + "#" + (filterHabitId ?: "")
        if (signature == chipSignature) return
        chipSignature = signature
        b.chipFilter.setOnCheckedStateChangeListener(null)
        b.chipFilter.removeAllViews()
        if (filterHabitId != null && latest.habits.none { it.id == filterHabitId }) filterHabitId = null

        fun addChip(label: String, habitId: String?) {
            val chip = layoutInflater.inflate(R.layout.item_chip, b.chipFilter, false) as Chip
            chip.id = View.generateViewId()
            chip.text = label
            chip.tag = habitId ?: ""
            chip.isChecked = (habitId == filterHabitId)
            b.chipFilter.addView(chip)
        }
        addChip(getString(R.string.all_habits), null)
        latest.habits.forEach { addChip(it.icon + " " + it.name, it.id) }
        b.chipFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = checkedIds.firstOrNull()?.let { group.findViewById<Chip>(it) }
            val tag = chip?.tag as? String
            filterHabitId = if (tag.isNullOrEmpty()) null else tag
            chipSignature = ""
            render()
        }
    }

    private fun buildWeekdayHeader() {
        val b = _binding ?: return
        b.weekdayRow.removeAllViews()
        DateUtils.weekdayLabels(Graph.settings.weekStart).forEach { label ->
            val tv = TextView(requireContext())
            tv.layoutParams = android.widget.LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            tv.gravity = Gravity.CENTER
            tv.text = label
            tv.textSize = 12f
            tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_muted))
            b.weekdayRow.addView(tv)
        }
    }

    private fun stateFor(date: LocalDate, today: LocalDate): Int {
        val habits = latest.habits.filter { filterHabitId == null || it.id == filterHabitId }
        val due = habits.filter { StreakCalculator.isDueOn(it.schedule(), date, today) }
        if (due.isEmpty()) return DayState.NONE
        if (date.isAfter(today)) return DayState.UPCOMING
        val done = due.count { latest.isDone(it.id, date) }
        return when {
            done == due.size -> DayState.DONE
            done > 0 -> DayState.PARTIAL
            date.isBefore(today) -> DayState.MISSED
            else -> DayState.UPCOMING
        }
    }

    private fun render() {
        val b = _binding ?: return
        val today = LocalDate.now()
        buildWeekdayHeader()
        b.tvMonth.text = DateUtils.monthTitle(month)

        val cells = ArrayList<DayCell>()
        repeat(DateUtils.leadingBlanks(month, Graph.settings.weekStart)) { cells.add(DayCell(null, DayState.NONE, false, false)) }
        for (day in 1..month.lengthOfMonth()) {
            val date = month.atDay(day)
            cells.add(DayCell(date, stateFor(date, today), date == today, date == selected))
        }
        adapter.submit(cells)

        val streak = latest.currentStreak(today)
        b.tvStreak.text = resources.getQuantityString(R.plurals.days_count, streak, streak)
        b.tvKeepGoing.visibility = if (streak > 0) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
