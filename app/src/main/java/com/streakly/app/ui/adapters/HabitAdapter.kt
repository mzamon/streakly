package com.streakly.app.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.streakly.app.R
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.databinding.ItemHabitBinding
import com.streakly.app.utils.ColorUtils

/** One line on the Home list. [subtitle] is already localised by the screen. */
data class HabitRow(
    val habit: HabitEntity,
    val done: Boolean,
    val due: Boolean,
    val subtitle: String
)

class HabitAdapter(
    private val onToggle: (HabitEntity, Boolean) -> Unit,
    private val onOpen: (HabitEntity) -> Unit
) : ListAdapter<HabitRow, HabitAdapter.Holder>(Diff) {

    class Holder(val b: ItemHabitBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val row = getItem(position)
        val b = holder.b
        val color = ColorUtils.parse(row.habit.color)

        b.tvIcon.setImageResource(ColorUtils.iconResource(b.root.context, row.habit.icon))
        b.tvIcon.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(ColorUtils.withAlpha(color, 48))
        }
        b.tvName.text = row.habit.name
        b.tvSub.text = row.subtitle
        b.root.alpha = if (row.due) 1f else 0.55f

        b.ivCheck.setBackgroundResource(if (row.done) R.drawable.bg_check_done else R.drawable.bg_check_empty)
        b.ivCheck.visibility = View.VISIBLE
        b.ivCheck.imageAlpha = if (row.done) 255 else 0
        b.checkHolder.isEnabled = row.due
        b.checkHolder.setOnClickListener {
            if (!row.due) return@setOnClickListener
            // Small "pop" so completing a habit feels rewarding
            b.ivCheck.animate().scaleX(1.3f).scaleY(1.3f).setDuration(110).withEndAction {
                b.ivCheck.animate().scaleX(1f).scaleY(1f).setDuration(110).start()
            }.start()
            onToggle(row.habit, !row.done)
        }
        b.root.setOnClickListener { onOpen(row.habit) }
    }

    private object Diff : DiffUtil.ItemCallback<HabitRow>() {
        override fun areItemsTheSame(a: HabitRow, b: HabitRow) = a.habit.id == b.habit.id
        override fun areContentsTheSame(a: HabitRow, b: HabitRow) = a == b
    }
}
