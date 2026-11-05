package com.streakly.app.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.StreaklyApplication
import com.streakly.app.databinding.ActivityChoiceBinding
import com.streakly.app.push.PushRegistrar

/** English, isiZulu and Setswana. The change applies instantly and is remembered by AppCompat. */
class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle(R.string.language)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tvHint.setText(R.string.language_hint)
        render()
    }

    private fun render() {
        val options = listOf(
            ChoiceScreen.Option("en", "English"),
            ChoiceScreen.Option("zu", "isiZulu"),
            ChoiceScreen.Option("tn", "Setswana"),
            ChoiceScreen.Option("system", getString(R.string.system_default))
        )
        ChoiceScreen.render(binding.optionsContainer, options, Graph.settings.language) { option ->
            Graph.settings.language = option.value
            StreaklyApplication.applyLanguage(option.value)
            // Let the server know so push notifications follow the new language
            if (Graph.auth.isSignedIn) PushRegistrar.enqueue(applicationContext)
            render()
        }
    }

    companion object {
        fun label(context: android.content.Context, tag: String): String = when (tag) {
            "en" -> "English"
            "zu" -> "isiZulu"
            "tn" -> "Setswana"
            else -> context.getString(R.string.system_default)
        }
    }
}
