package com.streakly.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.streakly.app.R
import com.streakly.app.databinding.ItemDayBinding
import java.time.LocalDate

object DayState {
    const val NONE = 0
    const val DONE = 1
    const val PARTIAL = 2
    const val MISSED = 3
    const val UPCOMING = 4
}

/** [date] is null for the blank cells before the first of the month. */
data class DayCell(val date: LocalDate?, val state: Int, val isToday: Boolean, val isSelected: Boolean)

class DayAdapter(private val onClick: (LocalDate) -> Unit) : RecyclerView.Adapter<DayAdapter.Holder>() {

    private var cells: List<DayCell> = emptyList()

    class Holder(val b: ItemDayBinding) : RecyclerView.ViewHolder(b.root)

    fun submit(list: List<DayCell>) {
        cells = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = cells.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cell = cells[position]
        val b = holder.b
        val ctx = b.root.context
        val date = cell.date
        if (date == null) {
            b.tvDay.text = ""
            b.tvDay.background = null
            b.dot.visibility = View.INVISIBLE
            b.root.setOnClickListener(null)
            b.root.isClickable = false
            return
        }
        b.tvDay.text = date.dayOfMonth.toString()
        b.root.isClickable = true
        b.root.setOnClickListener { onClick(date) }

        when {
            cell.isToday -> {
                b.tvDay.setBackgroundResource(R.drawable.bg_circle_orange)
                b.tvDay.setTextColor(ContextCompat.getColor(ctx, R.color.white))
            }
            cell.isSelected -> {
                b.tvDay.setBackgroundResource(R.drawable.bg_circle_flat)
                b.tvDay.setTextColor(ContextCompat.getColor(ctx, R.color.text_primary))
            }
            else -> {
                b.tvDay.background = null
                b.tvDay.setTextColor(ContextCompat.getColor(ctx, R.color.text_primary))
            }
        }

        val dotColor = when (cell.state) {
            DayState.DONE -> R.color.teal
            DayState.PARTIAL -> R.color.orange
            DayState.MISSED -> R.color.danger
            else -> 0
        }
        if (dotColor == 0) {
            b.dot.visibility = View.INVISIBLE
        } else {
            b.dot.visibility = View.VISIBLE
            ViewCompat.setBackgroundTintList(b.dot, android.content.res.ColorStateList.valueOf(ContextCompat.getColor(ctx, dotColor)))
        }
    }
}
