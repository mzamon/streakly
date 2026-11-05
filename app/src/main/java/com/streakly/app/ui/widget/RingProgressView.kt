package com.streakly.app.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.streakly.app.R

/** Circular progress ring with an orange-to-teal gradient (used on the My Progress screen). */
class RingProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val density = resources.displayMetrics.density
    private var strokePx = 14 * density
    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = ContextCompat.getColor(context, R.color.card_elevated)
    }
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val rect = RectF()
    private var animator: ValueAnimator? = null

    var progress: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 100f)
            invalidate()
        }

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.RingProgressView)
            strokePx = a.getDimension(R.styleable.RingProgressView_ringStroke, strokePx)
            a.recycle()
        }
        trackPaint.strokeWidth = strokePx
        arcPaint.strokeWidth = strokePx
    }

    fun animateTo(target: Float) {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(progress, target.coerceIn(0f, 100f)).apply {
            duration = 800
            interpolator = DecelerateInterpolator()
            addUpdateListener { progress = it.animatedValue as Float }
            start()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val half = strokePx / 2f
        rect.set(half + paddingLeft, half + paddingTop, w - half - paddingRight, h - half - paddingBottom)
        arcPaint.shader = SweepGradient(
            w / 2f, h / 2f,
            intArrayOf(
                ContextCompat.getColor(context, R.color.orange),
                ContextCompat.getColor(context, R.color.orange_light),
                ContextCompat.getColor(context, R.color.teal),
                ContextCompat.getColor(context, R.color.orange)
            ),
            floatArrayOf(0f, 0.45f, 0.9f, 1f)
        )
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(rect, 0f, 360f, false, trackPaint)
        if (progress <= 0f) return
        canvas.save()
        canvas.rotate(-90f, width / 2f, height / 2f)
        canvas.drawArc(rect, 0f, 360f * progress / 100f, false, arcPaint)
        canvas.restore()
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }
}
