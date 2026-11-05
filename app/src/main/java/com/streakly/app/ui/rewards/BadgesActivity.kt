package com.streakly.app.ui.rewards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.databinding.ActivityListScreenBinding
import com.streakly.app.ui.adapters.BadgeAdapter
import com.streakly.app.utils.Gamification
import kotlinx.coroutines.launch

/** Every badge, earned and locked. */
class BadgesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle(R.string.achievements)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val adapter = BadgeAdapter { badge: Gamification.Badge ->
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(BadgeText.title(badge.id)))
                .setMessage(
                    getString(BadgeText.description(badge.id)) + "\n\n" +
                        getString(if (badge.earned) R.string.unlocked else R.string.locked)
                )
                .setPositiveButton(R.string.close, null)
                .show()
        }
        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { adapter.submit(it.badges()) }
            }
        }
    }
}
