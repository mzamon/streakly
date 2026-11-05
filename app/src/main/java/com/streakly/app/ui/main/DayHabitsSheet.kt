package com.streakly.app.ui.main

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.model.AppSnapshot
import com.streakly.app.data.model.schedule
import com.streakly.app.databinding.ItemDayHabitBinding
import com.streakly.app.databinding.SheetDayHabitsBinding
import com.streakly.app.ui.common.snack
import com.streakly.app.utils.ColorUtils
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch
import java.time.LocalDate

/** Bottom sheet for one calendar day: complete or un-complete habits and edit the day's note. */
class DayHabitsSheet : BottomSheetDialogFragment() {

    private var _binding: SheetDayHabitsBinding? = null
    private val binding get() = _binding!!
    private lateinit var date: LocalDate
    private var latest: AppSnapshot = AppSnapshot.EMPTY
    private val rows = ArrayList<HabitEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SheetDayHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date = try { LocalDate.parse(requireArguments().getString(ARG_DATE)) } catch (e: Exception) { LocalDate.now() }
        binding.tvDate.text = DateUtils.fullDate(date)
        binding.tvHint.setText(if (date.isAfter(LocalDate.now())) R.string.future_day_hint else R.string.tap_to_complete)
        binding.rvDayHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDayHabits.adapter = Adapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { snapshot ->
                    latest = snapshot
                    rows.clear()
                    rows.addAll(snapshot.habits.filter { StreakCalculator.isDueOn(it.schedule(), date, LocalDate.now()) })
                    _binding?.let {
                        it.rvDayHabits.adapter?.notifyDataSetChanged()
                        it.tvEmpty.visibility = if (rows.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    private fun noteFor(habitId: String): String =
        latest.logs.firstOrNull { it.id == HabitLogEntity.idFor(habitId, date.toString()) }?.note ?: ""

    private fun editNote(habit: HabitEntity) {
        val input = EditText(requireContext())
        input.setText(noteFor(habit.id))
        input.setSelection(input.text.length)
        input.hint = getString(R.string.note_hint)
        val pad = (20 * resources.displayMetrics.density).toInt()
        val holder = android.widget.FrameLayout(requireContext())
        holder.setPadding(pad, pad / 2, pad, 0)
        holder.addView(input)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(habit.name)
            .setView(holder)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save) { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    Graph.habits.saveNote(habit.id, date.toString(), input.text.toString())
                }
            }
            .show()
    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.Holder>() {
        inner class Holder(val b: ItemDayHabitBinding) : RecyclerView.ViewHolder(b.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            Holder(ItemDayHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun getItemCount(): Int = rows.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val habit = rows[position]
            val b = holder.b
            val done = latest.isDone(habit.id, date)
            val future = date.isAfter(LocalDate.now())
            b.tvIcon.text = habit.icon
            b.tvIcon.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(ColorUtils.withAlpha(ColorUtils.parse(habit.color), 48))
            }
            b.tvName.text = habit.name
            val note = noteFor(habit.id)
            b.tvNote.visibility = if (note.isBlank()) View.GONE else View.VISIBLE
            b.tvNote.text = note
            b.ivCheck.setBackgroundResource(if (done) R.drawable.bg_check_done else R.drawable.bg_check_empty)
            b.ivCheck.imageAlpha = if (done) 255 else 0
            b.checkHolder.setOnClickListener {
                if (future) {
                    b.root.snack(R.string.future_day_hint)
                } else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        Graph.habits.setCompleted(habit.id, date.toString(), !done)
                    }
                }
            }
            b.btnNote.setOnClickListener { editNote(habit) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_DATE = "date"
        fun newInstance(isoDate: String): DayHabitsSheet = DayHabitsSheet().apply {
            arguments = Bundle().apply { putString(ARG_DATE, isoDate) }
        }
    }
}
