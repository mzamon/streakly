package com.streakly.app.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.databinding.ActivityPreferencesBinding

/** Theme, language, first day of the week and 12/24-hour clock. */
class PreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.rowTheme.setOnClickListener { startActivity(Intent(this, ThemeActivity::class.java)) }
        binding.rowLanguage.setOnClickListener { startActivity(Intent(this, LanguageActivity::class.java)) }
        binding.rowWeekStart.setOnClickListener {
            val values = listOf("MONDAY", "SUNDAY")
            val labels = arrayOf(getString(R.string.monday), getString(R.string.sunday))
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.week_start)
                .setSingleChoiceItems(labels, values.indexOf(Graph.settings.weekStart).coerceAtLeast(0)) { dialog, which ->
                    Graph.settings.weekStart = values[which]
                    dialog.dismiss()
                    refresh()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
        binding.rowTimeFormat.setOnClickListener {
            val values = listOf("system", "12", "24")
            val labels = arrayOf(getString(R.string.system_default), getString(R.string.time_12h), getString(R.string.time_24h))
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.time_format)
                .setSingleChoiceItems(labels, values.indexOf(Graph.settings.timeFormat).coerceAtLeast(0)) { dialog, which ->
                    Graph.settings.timeFormat = values[which]
                    dialog.dismiss()
                    refresh()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val s = Graph.settings
        binding.rowTheme.setValue(ThemeActivity.label(this, s.theme))
        binding.rowLanguage.setValue(LanguageActivity.label(this, s.language))
        binding.rowWeekStart.setValue(getString(if (s.weekStart == "SUNDAY") R.string.sunday else R.string.monday))
        binding.rowTimeFormat.setValue(
            when (s.timeFormat) {
                "12" -> getString(R.string.time_12h)
                "24" -> getString(R.string.time_24h)
                else -> getString(R.string.system_default)
            }
        )
    }
}
