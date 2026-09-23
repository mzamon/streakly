package com.streakly.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.LocaleListCompat
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.streakly.app.R
import com.streakly.app.data.repository.AuthRepository
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.session.SessionManager
import com.streakly.app.databinding.FragmentProfileBinding
import com.streakly.app.ui.settings.SettingsActivity
import com.streakly.app.ui.welcome.WelcomeActivity
import com.streakly.app.utils.Gamification
import com.streakly.app.utils.UiUtils
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var session: SessionManager
    private lateinit var habitRepo: HabitRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())
        habitRepo = HabitRepository(requireContext())

        binding.tvName.text = session.userName ?: "Streakly User"
        binding.tvEmail.text = session.userEmail ?: ""
        lifecycleScope.launch {
            val habits = habitRepo.getHabits()
            val total = habits.sumOf { habitRepo.getCompletionDates(it.localId).size }
            val points = Gamification.pointsForCompletionCount(total)
            binding.tvLevel.text = "Level " + Gamification.levelForPoints(points) + "  ·  $points XP"
        }

        binding.containerRows.removeAllViews()
        binding.containerRows.addView(row("⚙  Account Settings") {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        })
        binding.containerRows.addView(row("🌐  Language Preferences") { showLanguageDialog() })
        binding.containerRows.addView(row("ℹ  About & Help") {
            UiUtils.info(requireContext(), "About & Help",
                "Streakly - Build better habits.\n\nVersion 1.0")
        })
        binding.containerRows.addView(row("🚪  Logout") { confirmLogout() })
    }

    private fun row(label: String, onClick: () -> Unit): View {
        val wrap = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            val p = LinearLayout.LayoutParams(-1, -2).apply {
                setMargins(0, 8, 0, 8)
            }
            layoutParams = p
            setBackgroundResource(R.drawable.bg_card_dark)
            setPadding(40, 44, 40, 44)
            isClickable = true
            focusable = View.FOCUSABLE
            val outValue = android.util.TypedValue()
            requireContext().theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            setBackgroundResource(R.drawable.bg_card_dark)
            setOnClickListener { onClick() }
        }
        
        val tv = TextView(requireContext()).apply {
            text = label
            textSize = 16f
            setTextColor(requireContext().getColor(R.color.text_primary))
            val lp = LinearLayout.LayoutParams(0, -2, 1f)
            layoutParams = lp
        }
        
        val chevron = ImageView(requireContext()).apply {
            setImageResource(R.drawable.ic_chevron_right)
            val lp = LinearLayout.LayoutParams(48, 48)
            layoutParams = lp
        }
        
        wrap.addView(tv)
        wrap.addView(chevron)
        return wrap
    }

    private fun showLanguageDialog() {
        val labels = arrayOf("English", "isiZulu", "Setswana", "System Default")
        val values = arrayOf("en", "zu", "tn", "system")
        var checked = values.indexOf(session.language).coerceAtLeast(0)
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Language")
            .setSingleChoiceItems(labels, checked) { _, which -> checked = which }
            .setPositiveButton("OK") { _, _ ->
                session.language = values[checked]
                AppCompatDelegate.setApplicationLocales(
                    if (values[checked] == "system") LocaleListCompat.getEmptyLocaleList()
                    else LocaleListCompat.forLanguageTags(values[checked])
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmLogout() {
        UiUtils.confirm(requireContext(), "Logout",
            "Are you sure you want to log out of Streakly?", R.string.login) {
            AuthRepository(requireContext()).logout()
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}