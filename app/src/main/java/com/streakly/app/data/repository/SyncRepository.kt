package com.streakly.app.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.streakly.app.data.local.StreaklyDatabase
import com.streakly.app.data.local.entity.ConflictEntity
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity
import com.streakly.app.data.local.entity.SyncState
import com.streakly.app.data.remote.ApiService
import com.streakly.app.data.remote.dto.EntityBundle
import com.streakly.app.data.remote.dto.SyncRequest
import com.streakly.app.data.session.SessionManager
import com.streakly.app.utils.MergeDecision
import com.streakly.app.utils.NetworkMonitor
import com.streakly.app.utils.SyncMerge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class SyncOutcome {
    data class Success(val pushed: Int, val pulled: Int, val conflicts: Int) : SyncOutcome()
    object Offline : SyncOutcome()
    object NotSignedIn : SyncOutcome()
    data class Failed(val message: String) : SyncOutcome()
}

/**
 * Two-way synchronisation between Room and the REST API.
 *  1. Upload every PENDING row (each carries the server version it was based on).
 *  2. The server applies rows that are still based on the latest version (last write wins otherwise it
 *     reports a conflict) and returns everything that changed since our last cursor.
 *  3. Merge the changes into Room; rows edited on two devices become conflicts the user resolves.
 */
class SyncRepository(
    private val context: Context,
    private val db: StreaklyDatabase,
    private val auth: AuthRepository,
    private val settings: SessionManager,
    private val api: ApiService
) {
    private val mutex = Mutex()
    private val gson = Gson()

    suspend fun sync(): SyncOutcome = mutex.withLock {
        withContext(Dispatchers.IO) { doSync() }
    }

    private suspend fun doSync(): SyncOutcome {
        val uid = auth.uid ?: return SyncOutcome.NotSignedIn
        if (!NetworkMonitor.isOnline(context)) return SyncOutcome.Offline
        return try {
            val since = settings.sinceFor(uid)
            val pending = EntityBundle(
                habits = db.habitDao().getPending(uid),
                logs = db.habitLogDao().getPending(uid),
                rewards = db.rewardDao().getPending(uid),
                redemptions = db.redemptionDao().getPending(uid)
            )
            Log.d(TAG, "Sync start: pushing ${pending.size()} rows, since=$since")
            val response = api.sync(SyncRequest(since, pending))
            val pulled = applyChanges(uid, response.changes)
            val conflicts = applyConflicts(uid, response.conflicts)
            // Overlap the cursor by a few seconds so rows committed by another device in the same moment are not missed.
            settings.setSince(uid, (response.serverTime - CURSOR_OVERLAP_MS).coerceAtLeast(0L))
            settings.setLastSyncAt(uid, System.currentTimeMillis())
            Log.d(TAG, "Sync done: pulled=$pulled conflicts=$conflicts")
            SyncOutcome.Success(pending.size(), pulled, conflicts)
        } catch (e: HttpException) {
            Log.w(TAG, "Sync HTTP ${e.code()}")
            SyncOutcome.Failed("HTTP ${e.code()}")
        } catch (e: IOException) {
            Log.w(TAG, "Sync network problem: ${e.message}")
            SyncOutcome.Offline
        } catch (e: Exception) {
            Log.e(TAG, "Sync failed", e)
            SyncOutcome.Failed(e.message ?: "error")
        }
    }

    private suspend fun applyChanges(uid: String, changes: EntityBundle): Int {
        var count = 0
        for (remote in changes.habits) {
            val local = db.habitDao().getById(remote.id)
            val decision = SyncMerge.decide(local?.syncState, local?.updatedAt ?: 0L, remote.updatedAt)
            when (decision) {
                MergeDecision.TAKE_REMOTE -> db.habitDao().upsert(remote.copy(uid = uid, syncState = SyncState.SYNCED))
                MergeDecision.MARK_SYNCED -> db.habitDao().upsert(
                    local!!.copy(serverVersion = remote.serverVersion, syncState = SyncState.SYNCED)
                )
                MergeDecision.KEEP_LOCAL -> Unit
            }
            count++
        }
        for (remote in changes.logs) {
            val local = db.habitLogDao().getById(remote.id)
            when (SyncMerge.decide(local?.syncState, local?.updatedAt ?: 0L, remote.updatedAt)) {
                MergeDecision.TAKE_REMOTE -> db.habitLogDao().upsert(remote.copy(uid = uid, syncState = SyncState.SYNCED))
                MergeDecision.MARK_SYNCED -> db.habitLogDao().upsert(
                    local!!.copy(serverVersion = remote.serverVersion, syncState = SyncState.SYNCED)
                )
                MergeDecision.KEEP_LOCAL -> Unit
            }
            count++
        }
        for (remote in changes.rewards) {
            val local = db.rewardDao().getById(remote.id)
            when (SyncMerge.decide(local?.syncState, local?.updatedAt ?: 0L, remote.updatedAt)) {
                MergeDecision.TAKE_REMOTE -> db.rewardDao().upsert(remote.copy(uid = uid, syncState = SyncState.SYNCED))
                MergeDecision.MARK_SYNCED -> db.rewardDao().upsert(
                    local!!.copy(serverVersion = remote.serverVersion, syncState = SyncState.SYNCED)
                )
                MergeDecision.KEEP_LOCAL -> Unit
            }
            count++
        }
        for (remote in changes.redemptions) {
            val local = db.redemptionDao().getById(remote.id)
            when (SyncMerge.decide(local?.syncState, local?.updatedAt ?: 0L, remote.updatedAt)) {
                MergeDecision.TAKE_REMOTE -> db.redemptionDao().upsert(remote.copy(uid = uid, syncState = SyncState.SYNCED))
                MergeDecision.MARK_SYNCED -> db.redemptionDao().upsert(
                    local!!.copy(serverVersion = remote.serverVersion, syncState = SyncState.SYNCED)
                )
                MergeDecision.KEEP_LOCAL -> Unit
            }
            count++
        }
        return count
    }

    private suspend fun applyConflicts(uid: String, conflicts: EntityBundle): Int {
        var count = 0
        for (c in conflicts.habits) {
            val local = db.habitDao().getById(c.id) ?: continue
            db.habitDao().upsert(local.copy(syncState = SyncState.CONFLICT))
            saveConflict(uid, TYPE_HABIT, c.id, gson.toJson(c)); count++
        }
        for (c in conflicts.logs) {
            val local = db.habitLogDao().getById(c.id) ?: continue
            db.habitLogDao().upsert(local.copy(syncState = SyncState.CONFLICT))
            saveConflict(uid, TYPE_LOG, c.id, gson.toJson(c)); count++
        }
        for (c in conflicts.rewards) {
            val local = db.rewardDao().getById(c.id) ?: continue
            db.rewardDao().upsert(local.copy(syncState = SyncState.CONFLICT))
            saveConflict(uid, TYPE_REWARD, c.id, gson.toJson(c)); count++
        }
        for (c in conflicts.redemptions) {
            val local = db.redemptionDao().getById(c.id) ?: continue
            db.redemptionDao().upsert(local.copy(syncState = SyncState.CONFLICT))
            saveConflict(uid, TYPE_REDEMPTION, c.id, gson.toJson(c)); count++
        }
        return count
    }

    private suspend fun saveConflict(uid: String, type: String, entityId: String, json: String) {
        db.conflictDao().upsert(ConflictEntity(id = "$type:$entityId", uid = uid, type = type, entityId = entityId, serverJson = json))
    }

    /** Adopt the cloud version and discard the local edit. */
    suspend fun resolveKeepCloud(conflictId: String) {
        val uid = auth.uid ?: return
        val conflict = db.conflictDao().getById(conflictId) ?: return
        when (conflict.type) {
            TYPE_HABIT -> db.habitDao().upsert(
                gson.fromJson(conflict.serverJson, HabitEntity::class.java).copy(uid = uid, syncState = SyncState.SYNCED)
            )
            TYPE_LOG -> db.habitLogDao().upsert(
                gson.fromJson(conflict.serverJson, HabitLogEntity::class.java).copy(uid = uid, syncState = SyncState.SYNCED)
            )
            TYPE_REWARD -> db.rewardDao().upsert(
                gson.fromJson(conflict.serverJson, RewardEntity::class.java).copy(uid = uid, syncState = SyncState.SYNCED)
            )
            TYPE_REDEMPTION -> db.redemptionDao().upsert(
                gson.fromJson(conflict.serverJson, RedemptionEntity::class.java).copy(uid = uid, syncState = SyncState.SYNCED)
            )
        }
        db.conflictDao().delete(conflictId)
    }

    /** Keep the local edit: rebase it on the cloud version so the server accepts it on the next sync. */
    suspend fun resolveKeepLocal(conflictId: String) {
        val conflict = db.conflictDao().getById(conflictId) ?: return
        val now = System.currentTimeMillis()
        when (conflict.type) {
            TYPE_HABIT -> {
                val remote = gson.fromJson(conflict.serverJson, HabitEntity::class.java)
                db.habitDao().getById(conflict.entityId)?.let {
                    db.habitDao().upsert(it.copy(serverVersion = remote.serverVersion, updatedAt = now, syncState = SyncState.PENDING))
                }
            }
            TYPE_LOG -> {
                val remote = gson.fromJson(conflict.serverJson, HabitLogEntity::class.java)
                db.habitLogDao().getById(conflict.entityId)?.let {
                    db.habitLogDao().upsert(it.copy(serverVersion = remote.serverVersion, updatedAt = now, syncState = SyncState.PENDING))
                }
            }
            TYPE_REWARD -> {
                val remote = gson.fromJson(conflict.serverJson, RewardEntity::class.java)
                db.rewardDao().getById(conflict.entityId)?.let {
                    db.rewardDao().upsert(it.copy(serverVersion = remote.serverVersion, updatedAt = now, syncState = SyncState.PENDING))
                }
            }
            TYPE_REDEMPTION -> {
                val remote = gson.fromJson(conflict.serverJson, RedemptionEntity::class.java)
                db.redemptionDao().getById(conflict.entityId)?.let {
                    db.redemptionDao().upsert(it.copy(serverVersion = remote.serverVersion, updatedAt = now, syncState = SyncState.PENDING))
                }
            }
        }
        db.conflictDao().delete(conflictId)
    }

    fun observeConflicts(uid: String) = db.conflictDao().observeAll(uid)

    suspend fun conflictCount(): Int {
        val uid = auth.uid ?: return 0
        return db.conflictDao().getAll(uid).size
    }

    companion object {
        private const val TAG = "SyncRepository"
        private const val CURSOR_OVERLAP_MS = 5000L
        const val TYPE_HABIT = "habit"
        const val TYPE_LOG = "log"
        const val TYPE_REWARD = "reward"
        const val TYPE_REDEMPTION = "redemption"
    }
}
