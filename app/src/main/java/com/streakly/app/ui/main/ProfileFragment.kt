package com.streakly.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.databinding.FragmentProfileBinding
import com.streakly.app.ui.common.confirm
import com.streakly.app.ui.settings.AboutActivity
import com.streakly.app.ui.settings.NotificationsActivity
import com.streakly.app.ui.settings.OfflineSyncActivity
import com.streakly.app.ui.settings.SettingsActivity
import com.streakly.app.ui.settings.ThemeActivity
import com.streakly.app.ui.stats.StatsActivity
import com.streakly.app.ui.welcome.WelcomeActivity
import kotlinx.coroutines.launch

/** Presents account details and routes the user to profile, preference, and progress screens. */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ctx = requireContext()
        binding.rowSettings.setOnClickListener { startActivity(Intent(ctx, SettingsActivity::class.java)) }
        binding.rowProgress.setOnClickListener { startActivity(Intent(ctx, StatsActivity::class.java)) }
        binding.rowNotifications.setOnClickListener { startActivity(Intent(ctx, NotificationsActivity::class.java)) }
        binding.rowAppearance.setOnClickListener { startActivity(Intent(ctx, ThemeActivity::class.java)) }
        binding.rowSync.setOnClickListener { startActivity(Intent(ctx, OfflineSyncActivity::class.java)) }
        binding.rowPrivacy.setOnClickListener { startActivity(Intent(ctx, AboutActivity::class.java)) }
        binding.rowLogout.setOnClickListener {
            ctx.confirm(R.string.logout, R.string.logout_message, R.string.logout) {
                viewLifecycleOwner.lifecycleScope.launch {
                    Graph.auth.signOut()
                    startActivity(Intent(ctx, WelcomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    activity?.finish()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { snapshot ->
                    val b = _binding ?: return@collect
                    b.statStreak.set(getString(R.string.streak), snapshot.currentStreak().toString())
                    b.statHabits.set(getString(R.string.habits), snapshot.habits.size.toString())
                    b.statXp.set(getString(R.string.xp), snapshot.xp.toString())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bindUser()
    }

    private fun bindUser() {
        val b = _binding ?: return
        val user = Graph.auth.currentUser
        val name = user?.displayName?.takeIf { it.isNotBlank() } ?: user?.email?.substringBefore('@') ?: ""
        b.tvName.text = name
        b.tvEmail.text = user?.email ?: ""
        b.tvInitial.text = name.take(1).uppercase()
        val photo = user?.photoUrl
        if (photo != null) {
            b.ivAvatar.visibility = View.VISIBLE
            Glide.with(this).load(photo).circleCrop().into(b.ivAvatar)
        } else {
            b.ivAvatar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
