package com.streakly.app.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.streakly.app.R
import com.streakly.app.data.model.Reward
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.repository.RewardRepository
import com.streakly.app.databinding.DialogRedeemBinding
import com.streakly.app.databinding.FragmentRewardsBinding
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.Gamification
import com.streakly.app.utils.StreakCalculator
import kotlinx.coroutines.launch

class RewardsFragment : Fragment() {

    private var _binding: FragmentRewardsBinding? = null
    private val binding get() = _binding!!
    private lateinit var rewardsRepo: RewardRepository
    private lateinit var habitRepo: HabitRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rewardsRepo = RewardRepository(requireContext())
        habitRepo = HabitRepository(requireContext())
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        lifecycleScope.launch {
            val balance = rewardsRepo.pointsBalance()
            binding.tvPoints.text = balance.toString()
            binding.tvLevel.text = "Lv. " + Gamification.levelForPoints(balance)

            binding.containerRewards.removeAllViews()
            val rewards = rewardsRepo.getRewards()
            if (rewards.isEmpty()) binding.containerRewards.addView(emptyText(R.string.no_rewards_yet))
            for (reward in rewards) binding.containerRewards.addView(rewardRow(reward, balance))

            binding.containerBadges.removeAllViews()
            val habits = habitRepo.getHabits()
            val stats = habits.map { h ->
                val dates = habitRepo.getCompletionDates(h.localId).toSet()
                Triple(h, dates, StreakCalculator.stats(h, dates))
            }
            for (badge in Gamification.badgesFor(stats)) binding.containerBadges.addView(badgeTile(badge))

            binding.containerHistory.removeAllViews()
            val history = rewardsRepo.getRedemptions()
            if (history.isEmpty()) binding.containerHistory.addView(emptyText(R.string.no_history_yet))
            for (r in history) {
                binding.containerHistory.addView(historyRow(r.rewardName, r.cost, r.date))
            }
        }
    }

    private fun rewardRow(reward: Reward, balance: Int): View {
        val card = card()
        val icon = bigEmoji(reward.icon)
        val mid = column(reward.name, reward.description)
        val cost = TextView(requireContext()).apply {
            text = "${reward.cost} pts"
            setTextColor(requireContext().getColor(R.color.flame_500))
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        val redeem = TextView(requireContext()).apply {
            text = getString(R.string.redeem)
            val affordable = balance >= reward.cost
            alpha = if (affordable) 1f else 0.4f
            if (affordable) {
                setTextColor(requireContext().getColor(R.color.text_on_flame))
                setBackgroundResource(R.drawable.bg_button_flame)
                setOnClickListener { showRedeemSheet(reward, balance) }
            } else {
                setTextColor(requireContext().getColor(R.color.text_secondary))
                setBackgroundResource(R.drawable.bg_button_outline)
            }
            setPadding(26, 10, 26, 10)
        }
        card.addView(icon); card.addView(mid); card.addView(cost); card.addView(pad(redeem))
        return card
    }

    private fun showRedeemSheet(reward: Reward, balance: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val sheet = DialogRedeemBinding.inflate(layoutInflater)
        dialog.setContentView(sheet.root)
        sheet.tvRewardName.text = "${reward.icon}  ${reward.name}"
        sheet.tvCost.text = "${reward.cost} pts"
        sheet.tvBalance.text = getString(R.string.remaining_balance) + ": ${balance - reward.cost} pts"
        sheet.btnConfirm.setOnClickListener {
            lifecycleScope.launch {
                val result = rewardsRepo.redeem(reward)
                dialog.dismiss()
                if (result.isSuccess) refresh()
                else Toast.makeText(requireContext(), R.string.not_enough_points, Toast.LENGTH_SHORT).show()
            }
        }
        sheet.btnCancelDialog.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun badgeTile(badge: Gamification.Badge): View {
        val tile = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            val p = LinearLayout.LayoutParams(-2, -2)
            p.setMargins(0, 0, 20, 0)
            layoutParams = p
            setPadding(22, 20, 22, 20)
            setBackgroundResource(R.drawable.bg_card_dark)
            alpha = if (badge.earned) 1f else 0.35f
        }
        tile.addView(bigEmoji(badge.emoji))
        val name = TextView(requireContext()).apply {
            text = badge.name
            textSize = 11f
            setTextColor(requireContext().getColor(R.color.text_secondary))
            gravity = Gravity.CENTER
        }
        tile.addView(name)
        return tile
    }

    private fun historyRow(nameText: String, cost: Int, date: String): View {
        val row = card()
        row.addView(column(nameText, DateUtils.pretty(date)))
        val costTv = TextView(requireContext()).apply {
            text = "-$cost pts"
            setTextColor(requireContext().getColor(R.color.flame_500))
            textSize = 14f
        }
        row.addView(pad(costTv))
        return row
    }

    // ---------- small builders ----------
    private fun card() = LinearLayout(requireContext()).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val p = LinearLayout.LayoutParams(-1, -2)
        p.setMargins(32, 6, 32, 6)
        layoutParams = p
        setPadding(28, 20, 28, 20)
        setBackgroundResource(R.drawable.bg_card_dark)
    }

    private fun bigEmoji(e: String) = TextView(requireContext()).apply {
        text = e; textSize = 26f
        val p = LinearLayout.LayoutParams(-2, -2)
        p.setMargins(0, 0, 24, 0)
        layoutParams = p
    }

    private fun column(title: String, sub: String) = LinearLayout(requireContext()).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
        addView(TextView(requireContext()).apply {
            text = title; textSize = 16f; setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(requireContext().getColor(R.color.text_primary))
        })
        if (sub.isNotEmpty()) addView(TextView(requireContext()).apply {
            text = sub; textSize = 13f
            setTextColor(requireContext().getColor(R.color.text_secondary))
        })
    }

    private fun pad(v: View): View = LinearLayout(requireContext()).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        layoutParams = LinearLayout.LayoutParams(-2, -2)
        val p = LinearLayout.LayoutParams(-2, -2)
        p.setMargins(20, 0, 0, 0)
        v.layoutParams = p
        addView(v)
    }

    private fun emptyText(msg: Int) = TextView(requireContext()).apply {
        text = getString(msg)
        setTextColor(requireContext().getColor(R.color.text_muted))
        gravity = Gravity.CENTER
        setPadding(0, 24, 0, 24)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}