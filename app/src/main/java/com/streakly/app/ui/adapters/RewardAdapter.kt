package com.streakly.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.streakly.app.R
import com.streakly.app.data.local.entity.RewardEntity
import com.streakly.app.databinding.ItemRewardBinding

class RewardAdapter(
    private val onRedeem: (RewardEntity) -> Unit,
    private val onEdit: (RewardEntity) -> Unit
) : RecyclerView.Adapter<RewardAdapter.Holder>() {

    private var items: List<RewardEntity> = emptyList()
    private var balance: Int = 0

    class Holder(val b: ItemRewardBinding) : RecyclerView.ViewHolder(b.root)

    fun submit(list: List<RewardEntity>, balance: Int) {
        items = list
        this.balance = balance
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemRewardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val reward = items[position]
        val b = holder.b
        val affordable = balance >= reward.cost
        b.tvIcon.text = reward.icon
        b.tvName.text = reward.name
        b.tvCost.text = b.root.context.getString(R.string.cost_points, reward.cost)
        b.btnRedeem.isEnabled = affordable
        b.btnRedeem.alpha = if (affordable) 1f else 0.45f
        b.btnRedeem.setOnClickListener { onRedeem(reward) }
        b.btnEdit.setOnClickListener { onEdit(reward) }
    }
}
