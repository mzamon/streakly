package com.streakly.app.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.streakly.app.R
import com.streakly.app.data.model.Habit

class HabitAdapter(
    private val onCheck: (Habit) -> Unit,
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.VH>() {

    data class Entry(val habit: Habit, val streak: Int, val doneToday: Boolean)

    var entries: List<Entry> = emptyList()
        set(value) { field = value; notifyDataSetChanged() }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvIcon: TextView = v.findViewById(R.id.tvIcon)
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvSub: TextView = v.findViewById(R.id.tvSub)
        val btnCheck: TextView = v.findViewById(R.id.btnCheck)
        val root: View = v
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val e = entries[position]
        val ctx = holder.itemView.context
        holder.tvIcon.text = e.habit.icon
        holder.tvName.text = e.habit.name
        holder.tvSub.text = if (e.doneToday) "${e.streak}-day streak · done today"
            else "${e.streak}-day streak"

        holder.root.setOnClickListener { onClick(e.habit) }

        if (e.doneToday) {
            val d = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(ContextCompat.getColor(ctx, R.color.jade_500))
            }
            holder.btnCheck.background = d
            holder.btnCheck.text = "✓"
            holder.btnCheck.setOnClickListener { }
        } else {
            val d = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(ContextCompat.getColor(ctx, R.color.flame_500))
            }
            holder.btnCheck.background = d
            holder.btnCheck.text = "✓"
            holder.btnCheck.setOnClickListener { onCheck(e.habit) }
        }
    }

    override fun getItemCount(): Int = entries.size
}