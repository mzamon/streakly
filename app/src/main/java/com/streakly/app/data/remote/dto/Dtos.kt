package com.streakly.app.data.remote.dto

import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity

/** The four synchronised collections travel together in both directions. */
data class EntityBundle(
    val habits: List<HabitEntity> = emptyList(),
    val logs: List<HabitLogEntity> = emptyList(),
    val rewards: List<RewardEntity> = emptyList(),
    val redemptions: List<RedemptionEntity> = emptyList()
) {
    fun isEmpty(): Boolean = habits.isEmpty() && logs.isEmpty() && rewards.isEmpty() && redemptions.isEmpty()
    fun size(): Int = habits.size + logs.size + rewards.size + redemptions.size
}

/** POST /api/sync body: what changed on this device, plus the server version we last saw. */
data class SyncRequest(val since: Long, val changes: EntityBundle)

/** POST /api/sync response: everything changed on the server since [SyncRequest.since] and rows that clashed. */
data class SyncResponse(
    val serverTime: Long,
    val changes: EntityBundle = EntityBundle(),
    val conflicts: EntityBundle = EntityBundle()
)

data class DeviceRequest(
    val token: String,
    val language: String = "en",
    val timezone: String = "UTC",
    val platform: String = "android"
)

data class HealthResponse(val status: String = "", val time: Long = 0L, val store: String = "")

data class MessageResponse(val ok: Boolean = false, val message: String = "", val sent: Int = 0)
