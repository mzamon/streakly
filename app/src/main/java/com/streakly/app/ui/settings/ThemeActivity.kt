package com.streakly.app.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.StreaklyApplication
import com.streakly.app.databinding.ActivityChoiceBinding

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle(R.string.theme)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tvHint.setText(R.string.theme_hint)
        render()
    }

    private fun render() {
        val options = listOf(
            ChoiceScreen.Option("light", getString(R.string.theme_light)),
            ChoiceScreen.Option("dark", getString(R.string.theme_dark)),
            ChoiceScreen.Option("system", getString(R.string.system_default))
        )
        ChoiceScreen.render(binding.optionsContainer, options, Graph.settings.theme) { option ->
            Graph.settings.theme = option.value
            // Switching night mode recreates every open screen, so the new colours apply immediately.
            StreaklyApplication.applyTheme(option.value)
            render()
        }
    }

    companion object {
        fun label(context: android.content.Context, theme: String): String = when (theme) {
            "light" -> context.getString(R.string.theme_light)
            "system" -> context.getString(R.string.system_default)
            else -> context.getString(R.string.theme_dark)
        }
    }
}
