package com.streakly.app.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.streakly.app.R

/** Simple rounded bar chart: values are percentages (0-100), the highlighted bar is drawn in full orange. */
class BarChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val density = resources.displayMetrics.density
    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.card_elevated)
    }
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.text_muted)
        textSize = 11 * resources.displayMetrics.scaledDensity
        textAlign = Paint.Align.CENTER
    }
    private val rect = RectF()
    private var values: List<Float> = emptyList()
    private var labels: List<String> = emptyList()
    private var highlight: Int = -1

    fun setData(values: List<Float>, labels: List<String>, highlight: Int) {
        this.values = values
        this.labels = labels
        this.highlight = highlight
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (values.isEmpty()) return
        val labelSpace = 22 * density
        val chartHeight = height - labelSpace - paddingBottom
        val count = values.size
        val slot = (width - paddingLeft - paddingRight).toFloat() / count
        val barWidth = (slot * 0.5f).coerceAtMost(26 * density)
        val radius = barWidth / 2f
        val orange = ContextCompat.getColor(context, R.color.orange)
        val light = ContextCompat.getColor(context, R.color.orange_light)
        val dim = ContextCompat.getColor(context, R.color.orange_dark)

        for (i in 0 until count) {
            val cx = paddingLeft + slot * i + slot / 2f
            rect.set(cx - barWidth / 2f, paddingTop.toFloat(), cx + barWidth / 2f, chartHeight)
            canvas.drawRoundRect(rect, radius, radius, trackPaint)
            val h = ((values[i].coerceIn(0f, 100f) / 100f) * (chartHeight - paddingTop)).coerceAtLeast(if (values[i] > 0f) barWidth else 0f)
            if (h > 0f) {
                rect.set(cx - barWidth / 2f, chartHeight - h, cx + barWidth / 2f, chartHeight)
                val top = if (i == highlight) light else orange
                val bottom = if (i == highlight) orange else dim
                barPaint.shader = LinearGradient(0f, rect.top, 0f, rect.bottom, top, bottom, Shader.TileMode.CLAMP)
                canvas.drawRoundRect(rect, radius, radius, barPaint)
            }
            val label = labels.getOrNull(i) ?: ""
            canvas.drawText(label, cx, height - 4 * density, labelPaint)
        }
    }
}
