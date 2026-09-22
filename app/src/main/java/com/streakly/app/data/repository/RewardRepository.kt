package com.streakly.app.data.repository

import android.content.Context
import com.streakly.app.data.local.StreaklyDatabase
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity
import com.streakly.app.data.model.Redemption
import com.streakly.app.data.model.Reward
import com.streakly.app.utils.DateUtils
import com.streakly.app.utils.Gamification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RewardRepository(private val context: Context) {

    private val db = StreaklyDatabase.get(context)
    private val habitRepo = HabitRepository(context)

    suspend fun getRewards(): List<Reward> = withContext(Dispatchers.IO) {
        val list = db.rewardDao().getAll().map {
            Reward(it.id, it.remoteId, it.name, it.description, it.cost, it.icon)
        }
        if (list.isEmpty()) seedDefaults()
        db.rewardDao().getAll().map {
            Reward(it.id, it.remoteId, it.name, it.description, it.cost, it.icon)
        }
    }

    private suspend fun seedDefaults() {
        listOf(
            Reward(name = "Coffee treat", cost = 50, icon = "☕", description = "Small reward"),
            Reward(name = "Movie night", cost = 200, icon = "🎬", description = "Bigger reward"),
            Reward(name = "Rest day pass", cost = 30, icon = "🛋", description = "Skip a day")
        ).forEach { r ->
            db.rewardDao().upsert(RewardEntity(
                name = r.name, description = r.description, cost = r.cost, icon = r.icon
            ))
        }
    }

    suspend fun pointsBalance(): Int = withContext(Dispatchers.IO) {
        val habits = habitRepo.getHabits()
        var total = 0
        for (h in habits) total += habitRepo.getCompletionDates(h.localId).size
        val earned = Gamification.pointsForCompletionCount(total)
        val spent = db.redemptionDao().totalSpent()
        earned - spent
    }

    suspend fun getRedemptions(): List<Redemption> = withContext(Dispatchers.IO) {
        db.redemptionDao().getAll().map {
            Redemption(it.id, it.rewardId, it.rewardName, it.cost, it.date)
        }
    }

    suspend fun redeem(reward: Reward): Result<Unit> = withContext(Dispatchers.IO) {
        val balance = pointsBalance()
        if (balance < reward.cost) return@withContext Result.failure(Exception("Not enough points"))
        db.redemptionDao().upsert(
            RedemptionEntity(
                rewardId = reward.id, rewardName = reward.name,
                cost = reward.cost, date = DateUtils.today()
            )
        )
        Result.success(Unit)
    }

    suspend fun pushPending(): Boolean = true
    suspend fun refreshFromServer(): Boolean = true
    suspend fun clearAllLocalData() = withContext(Dispatchers.IO) {
        db.redemptionDao().deleteAll()
        db.rewardDao().deleteAll()
    }
}
