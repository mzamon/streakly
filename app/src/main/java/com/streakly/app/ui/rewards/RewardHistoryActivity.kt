package com.streakly.app.ui.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.databinding.ActivityListScreenBinding
import com.streakly.app.databinding.ItemRedemptionBinding
import com.streakly.app.utils.DateUtils
import kotlinx.coroutines.launch

/** Everything the user has redeemed, newest first. */
class RewardHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListScreenBinding
    private var items: List<RedemptionEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle(R.string.reward_history)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.tvEmpty.setText(R.string.empty_history)

        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun getItemCount(): Int = items.size
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
                object : RecyclerView.ViewHolder(ItemRedemptionBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {}
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val b = ItemRedemptionBinding.bind(holder.itemView)
                val item = items[position]
                b.tvName.text = item.rewardName
                b.tvDate.text = DateUtils.fullDate(item.date)
                b.tvCost.text = getString(R.string.spent_points, item.cost)
            }
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Graph.habits.observeSnapshot().collect { snapshot ->
                    items = snapshot.redemptions
                    adapter.notifyDataSetChanged()
                    binding.tvEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
                    binding.tvSummary.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
                    binding.tvSummary.text = getString(R.string.total_spent, snapshot.spent)
                }
            }
        }
    }
}
