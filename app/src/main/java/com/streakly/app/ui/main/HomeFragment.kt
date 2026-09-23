package com.streakly.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.session.SessionManager
import com.streakly.app.databinding.FragmentHomeBinding
import com.streakly.app.ui.adapters.HabitAdapter
import com.streakly.app.ui.habit.AddEditHabitActivity
import com.streakly.app.ui.habit.HabitDetailActivity
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.Gamification
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var repo: HabitRepository

    private val adapter = HabitAdapter(
        onCheck = { habit ->
            lifecycleScope.launch {
                repo.markDone(habit)
                refresh()
            }
        },
        onClick = { habit ->
            startActivity(
                Intent(requireContext(), HabitDetailActivity::class.java)
                    .putExtra(HabitDetailActivity.EXTRA_HABIT_ID, habit.localId)
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repo = HabitRepository(requireContext())
        binding.tvGreeting.text = getString(
            com.streakly.app.R.string.greeting,
            SessionManager(requireContext()).userName?.ifBlank { "there" } ?: "there"
        )
        binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.adapter = adapter
        binding.btnAddHabit.setOnClickListener {
            startActivity(Intent(requireContext(), AddEditHabitActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        lifecycleScope.launch {
            val habits = repo.getHabits()
            val entries = habits.map { h ->
                val dates = repo.getCompletionDates(h.localId).toSet()
                HabitAdapter.Entry(
                    habit = h,
                    streak = StreakCalculator.currentStreak(h, dates),
                    doneToday = dates.contains(DateUtils.today())
                )
            }
            adapter.entries = entries
            binding.layoutEmpty.visibility = if (entries.isEmpty()) View.VISIBLE else View.GONE

            val totalCompletions = habits.sumOf { repo.getCompletionDates(it.localId).size }
            val points = Gamification.pointsForCompletionCount(totalCompletions)
            binding.tvStreak.text = entries.maxOfOrNull { it.streak }?.toString() ?: "0"
            binding.tvLevel.text = "Lv. " + Gamification.levelForPoints(points)
            binding.tvPoints.text = "$points pts"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}