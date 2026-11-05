package com.streakly.app.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.streakly.app.databinding.ItemOptionBinding

/** Builds a single-choice list (used by Theme and Language) inside a card container. */
object ChoiceScreen {
    data class Option(val value: String, val title: String, val subtitle: String? = null)

    fun render(container: LinearLayout, options: List<Option>, selected: String, onPick: (Option) -> Unit) {
        container.removeAllViews()
        val inflater = LayoutInflater.from(container.context)
        options.forEachIndexed { index, option ->
            val row = ItemOptionBinding.inflate(inflater, container, false)
            row.tvTitle.text = option.title
            if (option.subtitle.isNullOrEmpty()) row.tvSubtitle.visibility = View.GONE else row.tvSubtitle.text = option.subtitle
            row.ivCheck.visibility = if (option.value == selected) View.VISIBLE else View.INVISIBLE
            row.root.setOnClickListener { onPick(option) }
            container.addView(row.root)
            if (index < options.size - 1) {
                val divider = View(container.context)
                divider.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
                divider.setBackgroundResource(com.streakly.app.R.color.stroke)
                container.addView(divider)
            }
        }
    }
}
