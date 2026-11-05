package com.streakly.app.data.repository

import android.content.Context
import com.streakly.app.data.local.StreaklyDatabase
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity
import com.streakly.app.data.local.entity.SyncState
import com.streakly.app.worker.SyncScheduler
import java.time.LocalDate
import java.util.UUID

enum class RedeemResult { SUCCESS, INSUFFICIENT, NOT_FOUND }

class RewardRepository(
    private val context: Context,
    private val db: StreaklyDatabase,
    private val auth: AuthRepository,
    private val habits: HabitRepository
) {
    fun newId(): String = UUID.randomUUID().toString()

    /**
     * Every account starts with three example rewards. The ids are fixed, so signing in on a second device
     * (or running this twice) never duplicates them.
     */
    suspend fun ensureDefaults() {
        val uid = auth.uid ?: return
        val defaults = listOf(
            Triple("default-coffee", "Coffee treat", 50) to "☕",
            Triple("default-movie", "Movie night", 200) to "ic_file",
            Triple("default-rest", "Guilt-free rest day", 120) to "ic_moon"
        )
        for ((triple, icon) in defaults) {
            if (db.rewardDao().getById("${uid}_${triple.first}") == null) {
                db.rewardDao().upsert(
                    RewardEntity(
                        id = "${uid}_${triple.first}", uid = uid, name = triple.second,
                        description = "", cost = triple.third, icon = icon,
                        updatedAt = 1L, syncState = SyncState.SYNCED
                    )
                )
            }
        }
    }

    suspend fun getReward(id: String): RewardEntity? = db.rewardDao().getById(id)

    suspend fun save(reward: RewardEntity) {
        val uid = auth.uid ?: return
        db.rewardDao().upsert(
            reward.copy(uid = uid, deleted = false, updatedAt = System.currentTimeMillis(), syncState = SyncState.PENDING)
        )
        SyncScheduler.requestNow(context)
    }

    suspend fun delete(id: String) {
        val reward = db.rewardDao().getById(id) ?: return
        db.rewardDao().upsert(
            reward.copy(deleted = true, updatedAt = System.currentTimeMillis(), syncState = SyncState.PENDING)
        )
        SyncScheduler.requestNow(context)
    }

    suspend fun redeem(rewardId: String): RedeemResult {
        val uid = auth.uid ?: return RedeemResult.NOT_FOUND
        val reward = db.rewardDao().getById(rewardId) ?: return RedeemResult.NOT_FOUND
        val snapshot = habits.snapshotOnce()
        if (!com.streakly.app.utils.Gamification.canAfford(snapshot.balance, reward.cost)) return RedeemResult.INSUFFICIENT
        db.redemptionDao().upsert(
            RedemptionEntity(
                id = UUID.randomUUID().toString(), uid = uid, rewardId = reward.id,
                rewardName = reward.name, cost = reward.cost, date = LocalDate.now().toString(),
                updatedAt = System.currentTimeMillis(), syncState = SyncState.PENDING
            )
        )
        SyncScheduler.requestNow(context)
        return RedeemResult.SUCCESS
    }
}
