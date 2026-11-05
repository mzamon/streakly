package com.streakly.app.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.streakly.app.R
import com.streakly.app.databinding.ViewSettingRowBinding

/** One tappable settings line: icon, title, optional value, and either a chevron or a switch. */
class SettingRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSettingRowBinding.inflate(LayoutInflater.from(context), this)

    var onCheckedChange: ((Boolean) -> Unit)? = null
    private var suppress = false

    init {
        orientation = HORIZONTAL
        gravity = android.view.Gravity.CENTER_VERTICAL
        val pad = (16 * resources.displayMetrics.density).toInt()
        setPadding(pad, 0, pad, 0)
        minimumHeight = (60 * resources.displayMetrics.density).toInt()
        val outValue = TypedValue()
        if (context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)) {
            setBackgroundResource(outValue.resourceId)
        }
        isClickable = true
        isFocusable = true

        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingRow)
        val iconRes = a.getResourceId(R.styleable.SettingRow_srIcon, 0)
        if (iconRes != 0) binding.ivIcon.setImageResource(iconRes) else binding.ivIcon.visibility = View.GONE
        binding.tvTitle.text = a.getString(R.styleable.SettingRow_srTitle) ?: ""
        val value = a.getString(R.styleable.SettingRow_srValue)
        if (value.isNullOrEmpty()) binding.tvValue.visibility = View.GONE else binding.tvValue.text = value
        val chevron = a.getBoolean(R.styleable.SettingRow_srChevron, true)
        val hasSwitch = a.getBoolean(R.styleable.SettingRow_srSwitch, false)
        val danger = a.getBoolean(R.styleable.SettingRow_srDanger, false)
        a.recycle()

        binding.ivChevron.visibility = if (chevron && !hasSwitch) View.VISIBLE else View.GONE
        binding.switchView.visibility = if (hasSwitch) View.VISIBLE else View.GONE
        if (danger) {
            val red = ContextCompat.getColor(context, R.color.danger)
            binding.tvTitle.setTextColor(red)
            ImageViewCompat.setImageTintList(binding.ivIcon, ColorStateList.valueOf(red))
        }
        if (hasSwitch) {
            binding.switchView.setOnCheckedChangeListener { _, checked ->
                if (!suppress) onCheckedChange?.invoke(checked)
            }
            setOnClickListener { binding.switchView.toggle() }
        }
    }

    fun setTitle(text: CharSequence) { binding.tvTitle.text = text }

    fun setValue(text: CharSequence?) {
        if (text.isNullOrEmpty()) {
            binding.tvValue.visibility = View.GONE
        } else {
            binding.tvValue.visibility = View.VISIBLE
            binding.tvValue.text = text
        }
    }

    /** Sets the switch without invoking the listener. */
    fun setChecked(checked: Boolean) {
        suppress = true
        binding.switchView.isChecked = checked
        suppress = false
    }

    fun isChecked(): Boolean = binding.switchView.isChecked
}
