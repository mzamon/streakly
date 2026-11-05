package com.streakly.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.databinding.ActivityOnboardingBinding
import com.streakly.app.databinding.ItemOnboardingBinding
import com.streakly.app.ui.common.SystemBars
import com.streakly.app.ui.common.dp
import com.streakly.app.ui.welcome.WelcomeActivity

/** Three introduction slides shown once, before the welcome screen. */
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    private data class Page(val icon: Int, val title: Int, val body: Int)

    private val pages = listOf(
        Page(R.drawable.ic_calendar, R.string.onboarding_title_1, R.string.onboarding_body_1),
        Page(R.drawable.ic_cloud, R.string.onboarding_title_2, R.string.onboarding_body_2),
        Page(R.drawable.ic_trophy, R.string.onboarding_title_3, R.string.onboarding_body_3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SystemBars.lightIcons(this, false)

        binding.pager.adapter = PageAdapter()
        buildDots()
        updateUi(0)
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) = updateUi(position)
        })
        binding.btnNext.setOnClickListener {
            val next = binding.pager.currentItem + 1
            if (next < pages.size) binding.pager.currentItem = next else finishOnboarding()
        }
        binding.btnSkip.setOnClickListener { finishOnboarding() }
    }

    private fun buildDots() {
        binding.dots.removeAllViews()
        pages.indices.forEach { _ ->
            val dot = ImageView(this)
            val lp = LinearLayout.LayoutParams(dp(10), dp(10))
            lp.marginStart = dp(5); lp.marginEnd = dp(5)
            dot.layoutParams = lp
            binding.dots.addView(dot)
        }
    }

    private fun updateUi(position: Int) {
        for (i in 0 until binding.dots.childCount) {
            (binding.dots.getChildAt(i) as ImageView).setImageResource(
                if (i == position) R.drawable.bg_dot_active else R.drawable.bg_dot_inactive
            )
        }
        binding.btnNext.setText(if (position == pages.size - 1) R.string.get_started else R.string.next)
        binding.btnSkip.visibility = if (position == pages.size - 1) android.view.View.INVISIBLE else android.view.View.VISIBLE
    }

    private fun finishOnboarding() {
        Graph.settings.onboardingDone = true
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    private inner class PageAdapter : RecyclerView.Adapter<PageAdapter.Holder>() {
        inner class Holder(val b: ItemOnboardingBinding) : RecyclerView.ViewHolder(b.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            Holder(ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun getItemCount(): Int = pages.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val page = pages[position]
            holder.b.tvEmoji.setImageResource(page.icon)
            holder.b.tvTitle.setText(page.title)
            holder.b.tvBody.setText(page.body)
        }
    }
}
