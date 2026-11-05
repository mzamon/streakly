package com.streakly.app.ui.splash

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.streakly.app.Graph
import com.streakly.app.databinding.ActivitySplashBinding
import com.streakly.app.ui.auth.AuthFlow
import com.streakly.app.ui.common.SystemBars
import com.streakly.app.ui.common.dp
import com.streakly.app.ui.onboarding.OnboardingActivity
import com.streakly.app.ui.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Animated launch screen: the flame ignites with an overshoot, a glow pulses behind it, ripples radiate
 * outwards, embers rise, and the wordmark tracks in. Afterwards the app routes to the right first screen.
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val running = mutableListOf<Animator>()
    private var routed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SystemBars.lightIcons(this, false)
        startAnimations()
        binding.root.setOnClickListener { route() }
        lifecycleScope.launch {
            delay(3000)
            route()
        }
    }

    private fun startAnimations() {
        val b = binding

        // 1. Initial state
        b.ivFlame.scaleX = 0.2f; b.ivFlame.scaleY = 0.2f; b.ivFlame.alpha = 0f; b.ivFlame.translationY = dp(60).toFloat()
        b.viewGlow.alpha = 0f; b.viewGlow.scaleX = 0.3f; b.viewGlow.scaleY = 0.3f
        b.tvWordmark.alpha = 0f; b.tvWordmark.translationY = dp(30).toFloat(); b.tvWordmark.letterSpacing = 0.45f
        b.tvTagline.alpha = 0f
        b.ivWave.translationY = dp(150).toFloat()
        for (r in listOf(b.ripple1, b.ripple2, b.ripple3)) { r.alpha = 0f }

        // 2. Flame ignites (scale + rise + fade, with overshoot)
        val ignite = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(b.ivFlame, View.SCALE_X, 0.2f, 1f),
                ObjectAnimator.ofFloat(b.ivFlame, View.SCALE_Y, 0.2f, 1f),
                ObjectAnimator.ofFloat(b.ivFlame, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(b.ivFlame, View.TRANSLATION_Y, dp(60).toFloat(), 0f)
            )
            duration = 900
            interpolator = OvershootInterpolator(1.8f)
        }

        // 3. Glow blooms, then keeps breathing
        val bloom = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(b.viewGlow, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(b.viewGlow, View.SCALE_X, 0.3f, 1f),
                ObjectAnimator.ofFloat(b.viewGlow, View.SCALE_Y, 0.3f, 1f)
            )
            duration = 1000
            interpolator = DecelerateInterpolator()
        }
        val breathe = listOf(
            loop(b.viewGlow, View.SCALE_X, 1f, 1.18f, 900, 1000),
            loop(b.viewGlow, View.SCALE_Y, 1f, 1.18f, 900, 1000)
        )

        // 4. Flicker: the flame sways and stretches like a real fire
        val flicker = listOf(
            loop(b.ivFlame, View.SCALE_Y, 1f, 1.07f, 520, 950),
            loop(b.ivFlame, View.ROTATION, -2.5f, 2.5f, 760, 950)
        )

        // 5. Ripples radiate from the flame
        val ripples = ArrayList<Animator>()
        for ((view, delay) in listOf(b.ripple1 to 0L, b.ripple2 to 650L, b.ripple3 to 1300L)) {
            ripples.add(loop(view, View.SCALE_X, 0.5f, 3.2f, 2000, 500 + delay, ValueAnimator.RESTART))
            ripples.add(loop(view, View.SCALE_Y, 0.5f, 3.2f, 2000, 500 + delay, ValueAnimator.RESTART))
            ripples.add(loop(view, View.ALPHA, 0.7f, 0f, 2000, 500 + delay, ValueAnimator.RESTART))
        }

        // 6. Wordmark tracks in, tagline fades up, wave slides in
        val word = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(b.tvWordmark, View.ALPHA, 0f, 1f),
                ObjectAnimator.ofFloat(b.tvWordmark, View.TRANSLATION_Y, dp(30).toFloat(), 0f),
                ValueAnimator.ofFloat(0.45f, 0.02f).apply {
                    addUpdateListener { b.tvWordmark.letterSpacing = it.animatedValue as Float }
                }
            )
            duration = 800
            startDelay = 600
            interpolator = DecelerateInterpolator()
        }
        val tagline = ObjectAnimator.ofFloat(b.tvTagline, View.ALPHA, 0f, 1f).apply { duration = 600; startDelay = 1200 }
        val wave = ObjectAnimator.ofFloat(b.ivWave, View.TRANSLATION_Y, dp(150).toFloat(), 0f).apply {
            duration = 900; startDelay = 300; interpolator = DecelerateInterpolator()
        }

        val all = ArrayList<Animator>()
        all.addAll(listOf(ignite, bloom, word, tagline, wave))
        all.addAll(breathe)
        all.addAll(flicker)
        all.addAll(ripples)
        running.addAll(all)
        all.forEach { it.start() }
    }

    /** An endlessly repeating property animation. */
    private fun loop(
        target: View, property: android.util.Property<View, Float>,
        from: Float, to: Float, durationMs: Long, delayMs: Long, mode: Int = ValueAnimator.REVERSE
    ): ObjectAnimator = ObjectAnimator.ofFloat(target, property, from, to).apply {
        duration = durationMs
        startDelay = delayMs
        repeatCount = ValueAnimator.INFINITE
        repeatMode = mode
        interpolator = if (mode == ValueAnimator.RESTART) LinearInterpolator() else AccelerateDecelerateInterpolator()
    }

    private fun route() {
        if (routed || isFinishing) return
        routed = true
        val target: Intent = when {
            !Graph.settings.onboardingDone && !Graph.auth.isSignedIn -> Intent(this, OnboardingActivity::class.java)
            !Graph.auth.isSignedIn -> Intent(this, WelcomeActivity::class.java)
            else -> AuthFlow.homeIntent(this)
        }
        // Forward a notification tap (deep link) to the main screen
        intent?.extras?.let { target.putExtras(it) }
        startActivity(target)
        finish()
        @Suppress("DEPRECATION")
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        running.forEach { it.cancel() }
        running.clear()
        super.onDestroy()
    }
}
