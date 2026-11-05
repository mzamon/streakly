package com.streakly.app.ui.rewards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.model.AppSnapshot
import com.streakly.app.databinding.FragmentRewardsBinding
import com.streakly.app.ui.adapters.BadgeAdapter
import com.streakly.app.ui.adapters.RewardAdapter
import com.streakly.app.utils.Gamification
import kotlinx.coroutines.launch

/** XP, level, achievements (badges) and the rewards the user can redeem with points. */
class RewardsFragment : Fragment() {

    private var _binding: FragmentRewardsBinding? = null
    private val binding get() = _binding!!
    private lateinit var badgeAdapter: BadgeAdapter
    private lateinit var rewardAdapter: RewardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRewardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        badgeAdapter = BadgeAdapter { badge -> showBadge(badge) }
        binding.rvBadges.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBadges.adapter = badgeAdapter

        rewardAdapter = RewardAdapter(
            onRedeem = { reward -> RedeemSheet.newInstance(reward.id).show(childFragmentManager, "redeem") },
            onEdit = { reward -> startActivity(CreateEditRewardActivity.intent(requireContext(), reward.id)) }
        )
        binding.rvRewards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRewards.adapter = rewardAdapter

        binding.btnCreate.setOnClickListener { startActivity(Intent(requireContext(), CreateEditRewardActivity::class.java)) }
        binding.tvSeeAll.setOnClickListener { startActivity(Intent(requireContext(), BadgesActivity::class.java)) }
        binding.tvHistory.setOnClickListener { startActivity(Intent(requireContext(), RewardHistoryActivity::class.java)) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { render(it) }
            }
        }
    }

    private fun showBadge(badge: Gamification.Badge) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle((if (badge.earned) badge.emoji else "🔒") + " " + getString(BadgeText.title(badge.id)))
            .setMessage(
                getString(BadgeText.description(badge.id)) + "\n\n" +
                    getString(if (badge.earned) R.string.unlocked else R.string.locked)
            )
            .setPositiveButton(R.string.close, null)
            .show()
    }

    private fun render(snapshot: AppSnapshot) {
        val b = _binding ?: return
        b.tvXp.text = getString(R.string.xp_format, snapshot.xp)
        b.tvLevel.text = getString(R.string.level_format, snapshot.level)
        b.progressLevel.max = Gamification.XP_PER_LEVEL
        b.progressLevel.setProgressCompat(Gamification.xpIntoLevel(snapshot.xp), true)
        b.tvToNext.text = getString(R.string.xp_to_next, Gamification.xpToNextLevel(snapshot.xp), snapshot.level + 1)
        b.tvBalance.text = getString(R.string.points_available, snapshot.balance)

        badgeAdapter.submit(snapshot.badges().take(4))
        rewardAdapter.submit(snapshot.rewards, snapshot.balance)
        b.tvEmptyRewards.visibility = if (snapshot.rewards.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
