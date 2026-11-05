package com.streakly.app.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.streakly.app.BuildConfig
import com.streakly.app.R
import com.streakly.app.data.remote.RetrofitClient
import com.streakly.app.databinding.ActivityAboutBinding
import com.streakly.app.ui.common.snack
import kotlinx.coroutines.launch

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tvVersion.text = getString(R.string.version_format, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        binding.rowApiStatus.setValue(getString(R.string.tap_to_check))

        binding.rowApiStatus.setOnClickListener { checkApi() }
        binding.rowPrivacy.setOnClickListener { open(BuildConfig.PRIVACY_URL) }
        binding.rowTerms.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.terms_title)
                .setMessage(R.string.terms_body)
                .setPositiveButton(R.string.close, null)
                .show()
        }
        binding.rowLicenses.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.open_source_licenses)
                .setMessage(R.string.licenses_body)
                .setPositiveButton(R.string.close, null)
                .show()
        }
        binding.rowSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(BuildConfig.SUPPORT_EMAIL))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_subject, BuildConfig.VERSION_NAME))
            }
            try { startActivity(intent) } catch (e: Exception) { binding.root.snack(R.string.error_no_email_app) }
        }
        binding.rowRate.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            } catch (e: Exception) {
                open("https://play.google.com/store/apps/details?id=$packageName")
            }
        }
    }

    private fun open(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            binding.root.snack(R.string.error_generic)
        }
    }

    /** Calls GET /health on the hosted REST API and shows the result. */
    private fun checkApi() {
        lifecycleScope.launch {
            binding.rowApiStatus.setValue(getString(R.string.checking))
            try {
                val health = RetrofitClient.api.health()
                binding.rowApiStatus.setValue(getString(R.string.api_online, health.store))
            } catch (e: Exception) {
                binding.rowApiStatus.setValue(getString(R.string.api_offline))
            }
        }
    }
}
