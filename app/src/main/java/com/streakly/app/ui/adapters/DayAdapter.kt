package com.streakly.app.ui.adapters

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.streakly.app.R

class DayAdapter(
    private val context: Context,
    private val cells: List<DayCell>
) : BaseAdapter() {

    override fun getCount(): Int = cells.size
    override fun getItem(position: Int): Any = cells[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tv = (convertView as? TextView) ?: TextView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = android.view.Gravity.CENTER
            textSize = 12f
        }

        val cell = cells[position]
        val sizePx = (parent?.width ?: 300) / 7 - 12
        tv.layoutParams = ViewGroup.LayoutParams(sizePx, sizePx)

        if (cell.date == null) {
            tv.text = ""
            tv.background = null
            return tv
        }

        tv.text = cell.date.dayOfMonth.toString()
        val color = when (cell.state) {
            DayState.DONE -> R.color.jade_500
            DayState.MISSED -> R.color.text_muted
            DayState.NOT_DUE -> R.color.sand_200
            DayState.TODAY -> R.color.streakly_teal
            DayState.AT_RISK -> R.color.flame_500
            DayState.BLANK -> R.color.sand_100
        }
        val d = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(ContextCompat.getColor(context, color))
        }
        tv.background = d
        tv.setTextColor(ContextCompat.getColor(context,
            if (cell.state == DayState.NOT_DUE || cell.state == DayState.BLANK) R.color.text_primary
            else R.color.text_on_teal))
        return tv
    }
}



