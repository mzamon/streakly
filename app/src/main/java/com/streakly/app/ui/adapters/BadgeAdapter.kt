package com.streakly.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.streakly.app.R
import com.streakly.app.databinding.ItemBadgeBinding
import com.streakly.app.ui.rewards.BadgeText
import com.streakly.app.utils.Gamification

class BadgeAdapter(private val onClick: (Gamification.Badge) -> Unit) : RecyclerView.Adapter<BadgeAdapter.Holder>() {

    private var items: List<Gamification.Badge> = emptyList()

    class Holder(val b: ItemBadgeBinding) : RecyclerView.ViewHolder(b.root)

    fun submit(list: List<Gamification.Badge>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemBadgeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val badge = items[position]
        val b = holder.b
        val ctx = b.root.context
        b.tvEmoji.text = if (badge.earned) badge.emoji else "🔒"
        b.tvTitle.setText(BadgeText.title(badge.id))
        b.tvState.setText(if (badge.earned) R.string.unlocked else R.string.locked)
        b.tvState.setTextColor(ContextCompat.getColor(ctx, if (badge.earned) R.color.teal else R.color.text_muted))
        b.root.setBackgroundResource(if (badge.earned) R.drawable.bg_badge_earned else R.drawable.bg_card)
        b.root.alpha = if (badge.earned) 1f else 0.7f
        b.root.setOnClickListener { onClick(badge) }
    }
}
