package com.streakly.app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.streakly.app.R
import com.streakly.app.databinding.ViewStatCardBinding

/** Small rounded tile with a caption and a big number (used on Habit detail, Profile and Progress). */
class StatCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewStatCardBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
        setBackgroundResource(R.drawable.bg_card)
        val pad = (14 * resources.displayMetrics.density).toInt()
        setPadding(pad, pad, pad, pad)
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingRow)
        binding.tvStatLabel.text = a.getString(R.styleable.SettingRow_srTitle) ?: ""
        a.recycle()
    }

    fun set(label: CharSequence, value: CharSequence) {
        binding.tvStatLabel.text = label
        binding.tvStatValue.text = value
    }

    fun setValue(value: CharSequence) {
        binding.tvStatValue.text = value
    }

    fun setLabel(label: CharSequence) {
        binding.tvStatLabel.text = label
    }
}
