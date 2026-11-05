package com.streakly.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.model.AppSnapshot
import com.streakly.app.data.model.schedule
import com.streakly.app.databinding.FragmentHomeBinding
import com.streakly.app.ui.adapters.HabitAdapter
import com.streakly.app.ui.adapters.HabitRow
import com.streakly.app.ui.habit.AddEditHabitActivity
import com.streakly.app.ui.habit.HabitDetailActivity
import com.streakly.app.ui.settings.NotificationsActivity
import com.streakly.app.ui.stats.StatsActivity
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.Gamification
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

/** Today's habits, current streak, level progress and the "streak at risk" alert. */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HabitAdapter
    private var latest: AppSnapshot = AppSnapshot.EMPTY

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HabitAdapter(
            onToggle = { habit, done ->
                viewLifecycleOwner.lifecycleScope.launch {
                    Graph.habits.setCompleted(habit.id, LocalDate.now().toString(), done)
                }
            },
            onOpen = { habit -> startActivity(HabitDetailActivity.intent(requireContext(), habit.id)) }
        )
        binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.adapter = adapter

        val add = View.OnClickListener { startActivity(Intent(requireContext(), AddEditHabitActivity::class.java)) }
        binding.fabAdd.setOnClickListener(add)
        binding.btnAddFirst.setOnClickListener(add)
        binding.cardStreak.setOnClickListener { startActivity(Intent(requireContext(), StatsActivity::class.java)) }
        binding.btnBell.setOnClickListener { startActivity(Intent(requireContext(), NotificationsActivity::class.java)) }
        binding.btnRiskDone.setOnClickListener {
            val habit = latest.atRisk().firstOrNull() ?: return@setOnClickListener
            viewLifecycleOwner.lifecycleScope.launch {
                Graph.habits.setCompleted(habit.id, LocalDate.now().toString(), true)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { render(it) }
            }
        }
    }

    private fun greeting(): String {
        val hour = LocalDateTime.now().hour
        val name = Graph.auth.currentUser?.displayName?.trim()?.substringBefore(' ')
            ?: Graph.auth.currentUser?.email?.substringBefore('@') ?: ""
        val res = when {
            hour < 12 -> R.string.greeting_morning
            hour < 17 -> R.string.greeting_afternoon
            else -> R.string.greeting_evening
        }
        return getString(res, name)
    }

    private fun render(snapshot: AppSnapshot) {
        val b = _binding ?: return
        latest = snapshot
        val today = LocalDate.now()
        val settings = Graph.settings

        b.tvGreeting.text = greeting()
        b.tvDate.text = DateUtils.fullDate(today)

        val streak = snapshot.currentStreak(today)
        b.tvStreakCount.text = streak.toString()
        b.tvStreakLabel.text = resources.getQuantityString(R.plurals.day_streak, streak, streak)
        b.tvLevel.text = getString(R.string.level_format, snapshot.level)
        b.tvXp.text = getString(R.string.points_format, snapshot.balance)
        b.progressLevel.max = Gamification.XP_PER_LEVEL
        b.progressLevel.setProgressCompat(Gamification.xpIntoLevel(snapshot.xp), true)

        // Streak-at-risk alert (shown from midday while a running streak is still unlogged today)
        val risky = snapshot.atRisk(today)
        if (risky.isNotEmpty() && LocalDateTime.now().hour >= 12) {
            val habit = risky.first()
            val habitStreak = StreakCalculator.currentStreak(
                habit.schedule(), snapshot.doneDates(habit.id), today
            )
            b.tvRisk.text = resources.getQuantityString(R.plurals.streak_risk_banner, habitStreak, habitStreak, habit.name)
            b.bannerRisk.visibility = View.VISIBLE
        } else {
            b.bannerRisk.visibility = View.GONE
        }

        val rows = snapshot.habits.map { habit ->
            val due = StreakCalculator.isDueOn(habit.schedule(), today, today)
            val done = snapshot.isDone(habit.id, today)
            val stats = snapshot.statsFor(habit, today)
            val parts = ArrayList<String>()
            if (!due) parts.add(getString(R.string.not_due_today))
            else if (stats.currentStreak > 0) parts.add(resources.getQuantityString(R.plurals.streak_days_inline, stats.currentStreak, stats.currentStreak))
            else parts.add(getString(R.string.start_streak))
            if (habit.reminderHour >= 0) parts.add(DateUtils.formatTime(requireContext(), habit.reminderHour, habit.reminderMinute, settings.timeFormat))
            HabitRow(habit, done, due, parts.joinToString(" · "))
        }.sortedWith(compareBy<HabitRow>({ !it.due }, { it.done }))

        adapter.submitList(rows)
        val dueRows = rows.filter { it.due }
        b.tvTodayCount.text = getString(R.string.done_of_total, dueRows.count { it.done }, dueRows.size)
        b.tvTodayCount.visibility = if (dueRows.isEmpty()) View.GONE else View.VISIBLE
        b.emptyState.visibility = if (rows.isEmpty()) View.VISIBLE else View.GONE
        b.rvHabits.visibility = if (rows.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
