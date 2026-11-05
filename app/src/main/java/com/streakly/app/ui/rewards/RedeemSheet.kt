package com.streakly.app.ui.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.RedeemResult
import com.streakly.app.databinding.SheetRedeemBinding
import com.streakly.app.utils.ColorUtils
import kotlinx.coroutines.launch

/** Confirmation sheet: shows the cost and the balance that will remain before points are spent. */
class RedeemSheet : BottomSheetDialogFragment() {

    private var _binding: SheetRedeemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SheetRedeemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rewardId = requireArguments().getString(ARG_ID) ?: run { dismiss(); return }
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnConfirm.isEnabled = false

        viewLifecycleOwner.lifecycleScope.launch {
            val reward = Graph.rewards.getReward(rewardId)
            if (reward == null || reward.deleted) { dismiss(); return@launch }
            val balance = Graph.habits.snapshotOnce().balance
            val b = _binding ?: return@launch
            b.tvIcon.setImageResource(ColorUtils.iconResource(requireContext(), reward.icon))
            b.tvName.text = reward.name
            b.tvCost.text = getString(R.string.cost_points, reward.cost)
            b.tvAfter.text = getString(R.string.balance_after, balance, (balance - reward.cost).coerceAtLeast(0))
            b.btnConfirm.isEnabled = balance >= reward.cost
            if (balance < reward.cost) b.tvAfter.setText(R.string.not_enough_points)
            b.btnConfirm.setOnClickListener {
                b.btnConfirm.isEnabled = false
                viewLifecycleOwner.lifecycleScope.launch {
                    val ctx = requireContext().applicationContext
                    when (Graph.rewards.redeem(reward.id)) {
                        RedeemResult.SUCCESS -> Toast.makeText(ctx, ctx.getString(R.string.redeemed_toast, reward.name), Toast.LENGTH_LONG).show()
                        RedeemResult.INSUFFICIENT -> Toast.makeText(ctx, R.string.not_enough_points, Toast.LENGTH_LONG).show()
                        RedeemResult.NOT_FOUND -> Toast.makeText(ctx, R.string.error_generic, Toast.LENGTH_LONG).show()
                    }
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ID = "reward_id"
        fun newInstance(rewardId: String): RedeemSheet = RedeemSheet().apply {
            arguments = Bundle().apply { putString(ARG_ID, rewardId) }
        }
    }
}
