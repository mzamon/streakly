package com.streakly.app.ui.rewards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.local.entity.RewardEntity
import com.streakly.app.databinding.ActivityCreateEditRewardBinding
import com.streakly.app.ui.common.clearErrorOnType
import com.streakly.app.ui.common.confirm
import com.streakly.app.ui.common.text
import com.streakly.app.utils.ColorUtils
import com.streakly.app.utils.Validators
import kotlinx.coroutines.launch

class CreateEditRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEditRewardBinding
    private var editing: RewardEntity? = null
    private var icon: String = ColorUtils.REWARD_ICONS[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEditRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.setTitle(R.string.new_reward)
        binding.tilName.clearErrorOnType()
        binding.tilCost.clearErrorOnType()
        buildIcons()

        binding.btnSave.setOnClickListener { save() }
        binding.btnDelete.setOnClickListener {
            val reward = editing ?: return@setOnClickListener
            confirm(R.string.delete_reward, R.string.delete_reward_message, R.string.delete) {
                lifecycleScope.launch {
                    Graph.rewards.delete(reward.id)
                    finish()
                }
            }
        }

        val id = intent.getStringExtra(EXTRA_ID)
        if (id != null) {
            lifecycleScope.launch {
                val reward = Graph.rewards.getReward(id)
                if (reward == null) finish() else load(reward)
            }
        }
    }

    private fun load(reward: RewardEntity) {
        editing = reward
        binding.toolbar.setTitle(R.string.edit_reward)
        binding.etName.setText(reward.name)
        binding.etCost.setText(reward.cost.toString())
        binding.etDescription.setText(reward.description)
        icon = reward.icon
        buildIcons()
        binding.btnDelete.visibility = View.VISIBLE
    }

    private fun buildIcons() {
        binding.chipIcons.removeAllViews()
        ColorUtils.REWARD_ICONS.forEach { emoji ->
            val chip = layoutInflater.inflate(R.layout.item_chip, binding.chipIcons, false) as Chip
            chip.id = View.generateViewId()
            chip.text = emoji
            chip.textSize = 20f
            chip.isChecked = emoji == icon
            chip.setOnClickListener { icon = emoji }
            binding.chipIcons.addView(chip)
        }
    }

    private fun save() {
        val name = binding.tilName.text
        val cost = binding.tilCost.text.toIntOrNull()
        var valid = true
        if (name.isBlank() || name.length > 40) {
            binding.tilName.error = getString(R.string.error_reward_name)
            valid = false
        }
        if (!Validators.isValidRewardCost(cost)) {
            binding.tilCost.error = getString(R.string.error_reward_cost)
            valid = false
        }
        if (!valid) return
        val description = binding.etDescription.text.toString().trim()
        val reward = editing?.copy(name = name, cost = cost!!, description = description, icon = icon)
            ?: RewardEntity(id = Graph.rewards.newId(), uid = "", name = name, description = description, cost = cost!!, icon = icon)
        lifecycleScope.launch {
            Graph.rewards.save(reward)
            finish()
        }
    }

    companion object {
        const val EXTRA_ID = "reward_id"
        fun intent(context: Context, rewardId: String): Intent =
            Intent(context, CreateEditRewardActivity::class.java).putExtra(EXTRA_ID, rewardId)
    }
}
