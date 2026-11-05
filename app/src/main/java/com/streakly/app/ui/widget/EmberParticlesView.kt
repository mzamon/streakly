package com.streakly.app.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.sin
import kotlin.random.Random

/** Rising orange embers used on the splash screen. Pure canvas drawing, no assets required. */
class EmberParticlesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private class Ember(var x: Float, var y: Float, var speed: Float, var radius: Float, var sway: Float, var phase: Float, var warm: Boolean)

    private val density = resources.displayMetrics.density
    private val embers = ArrayList<Ember>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var lastNanos = 0L
    private var time = 0f

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener {
            step()
            invalidate()
        }
    }

    private fun spawn(startAnywhere: Boolean): Ember {
        val w = width.toFloat().coerceAtLeast(1f)
        val h = height.toFloat().coerceAtLeast(1f)
        return Ember(
            x = Random.nextFloat() * w,
            y = if (startAnywhere) Random.nextFloat() * h else h + Random.nextFloat() * 40 * density,
            speed = (28 + Random.nextFloat() * 70) * density,
            radius = (1.2f + Random.nextFloat() * 3.2f) * density,
            sway = (6 + Random.nextFloat() * 18) * density,
            phase = Random.nextFloat() * 6.28f,
            warm = Random.nextBoolean()
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        embers.clear()
        repeat(46) { embers.add(spawn(true)) }
    }

    private fun step() {
        val now = System.nanoTime()
        val dt = if (lastNanos == 0L) 0.016f else ((now - lastNanos) / 1_000_000_000f).coerceAtMost(0.05f)
        lastNanos = now
        time += dt
        for (i in embers.indices) {
            val e = embers[i]
            e.y -= e.speed * dt
            e.x += sin(time * 1.6f + e.phase) * e.sway * dt
            if (e.y < -10 * density) embers[i] = spawn(false)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val h = height.toFloat().coerceAtLeast(1f)
        for (e in embers) {
            val life = (e.y / h).coerceIn(0f, 1f)
            val alpha = (life * 0.9f * 255).toInt().coerceIn(0, 255)
            paint.color = if (e.warm) Color.argb(alpha, 255, 122, 0) else Color.argb(alpha, 255, 209, 102)
            canvas.drawCircle(e.x, e.y, e.radius, paint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lastNanos = 0L
        animator.start()
    }

    override fun onDetachedFromWindow() {
        animator.cancel()
        super.onDetachedFromWindow()
    }
}
