package com.streakly.app.ui.settings

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.repository.AuthResult
import com.streakly.app.data.repository.ProfilePhotoRepository
import com.streakly.app.databinding.ActivityPersonalDetailsBinding
import com.streakly.app.ui.common.clearErrorOnType
import com.streakly.app.ui.common.snack
import com.streakly.app.ui.common.text
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.Validators
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

/** Name, phone, date of birth and profile photo (stored in Firebase Storage). */
class PersonalDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDetailsBinding
    private var dob: String = ""

    private val pickPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) uploadPhoto(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tilName.clearErrorOnType()
        binding.tilPhone.clearErrorOnType()

        val user = Graph.auth.currentUser
        val uid = Graph.auth.uid ?: run { finish(); return }
        binding.etName.setText(user?.displayName ?: "")
        binding.etEmail.setText(user?.email ?: "")
        binding.etPhone.setText(Graph.settings.profileField(uid, "phone"))
        dob = Graph.settings.profileField(uid, "dob")
        showDob()
        renderPhoto()

        binding.btnCamera.setOnClickListener { pickPhoto.launch("image/*") }
        binding.btnRemovePhoto.setOnClickListener { removePhoto() }
        binding.etDob.setOnClickListener { pickDob() }
        binding.tilDob.setEndIconOnClickListener { pickDob() }
        binding.btnSave.setOnClickListener { save(uid) }
    }

    private fun showDob() {
        binding.etDob.setText(if (dob.isBlank()) "" else DateUtils.fullDate(dob))
    }

    private fun pickDob() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.dob_hint)
            .setSelection(
                try {
                    LocalDate.parse(dob).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                } catch (e: Exception) {
                    MaterialDatePicker.todayInUtcMilliseconds()
                }
            )
            .build()
        picker.addOnPositiveButtonClickListener { millis ->
            val date = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
            dob = if (date.isAfter(LocalDate.now())) "" else date.toString()
            showDob()
        }
        picker.show(supportFragmentManager, "dob")
    }

    private fun renderPhoto() {
        val user = Graph.auth.currentUser
        val name = user?.displayName?.takeIf { it.isNotBlank() } ?: user?.email ?: ""
        binding.tvInitial.text = name.take(1).uppercase()
        val photo = user?.photoUrl
        if (photo != null) {
            binding.ivAvatar.visibility = View.VISIBLE
            Glide.with(this).load(photo).circleCrop().into(binding.ivAvatar)
            binding.btnRemovePhoto.visibility = View.VISIBLE
        } else {
            binding.ivAvatar.visibility = View.GONE
            binding.btnRemovePhoto.visibility = View.GONE
        }
    }

    private fun uploadPhoto(uri: android.net.Uri) {
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            when (val result = ProfilePhotoRepository.upload(applicationContext, uri)) {
                is AuthResult.Success -> { binding.tvStatus.setText(R.string.photo_updated); renderPhoto() }
                is AuthResult.Error -> binding.tvStatus.setText(result.messageRes)
                is AuthResult.Cancelled -> Unit
            }
            binding.progress.visibility = View.INVISIBLE
        }
    }

    private fun removePhoto() {
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            when (val result = ProfilePhotoRepository.remove()) {
                is AuthResult.Success -> { binding.tvStatus.setText(R.string.photo_removed); renderPhoto() }
                is AuthResult.Error -> binding.tvStatus.setText(result.messageRes)
                is AuthResult.Cancelled -> Unit
            }
            binding.progress.visibility = View.INVISIBLE
        }
    }

    private fun save(uid: String) {
        val name = binding.tilName.text
        val phone = binding.tilPhone.text
        var valid = true
        if (name.isBlank()) { binding.tilName.error = getString(R.string.error_name_required); valid = false }
        if (!Validators.isValidPhone(phone)) { binding.tilPhone.error = getString(R.string.error_phone); valid = false }
        if (!valid) return
        lifecycleScope.launch {
            binding.btnSave.isEnabled = false
            val result = if (name != (Graph.auth.currentUser?.displayName ?: "")) Graph.auth.updateDisplayName(name) else AuthResult.Success
            if (result is AuthResult.Error) {
                binding.tvStatus.setText(result.messageRes)
            } else {
                Graph.settings.setProfileField(uid, "phone", phone)
                Graph.settings.setProfileField(uid, "dob", dob)
                binding.root.snack(R.string.details_saved)
                binding.tvStatus.text = ""
            }
            binding.btnSave.isEnabled = true
        }
    }
}
