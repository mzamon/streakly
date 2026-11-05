package com.streakly.app.data.repository

import android.content.Context
import android.util.Log
import com.streakly.app.data.local.StreaklyDatabase
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.local.entity.SyncState
import com.streakly.app.data.model.AppSnapshot
import com.streakly.app.worker.ReminderScheduler
import com.streakly.app.worker.SyncScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.util.UUID

/**
 * All habit and log writes go to Room first (so they work offline), are flagged PENDING and then a
 * background sync is requested. The UI observes [observeSnapshot].
 */
class HabitRepository(
    private val context: Context,
    private val db: StreaklyDatabase,
    private val auth: AuthRepository
) {
    fun newId(): String = UUID.randomUUID().toString()

    fun observeSnapshot(): Flow<AppSnapshot> {
        val uid = auth.uid ?: return flowOf(AppSnapshot.EMPTY)
        return combine(
            db.habitDao().observeActive(uid),
            db.habitLogDao().observeAll(uid),
            db.rewardDao().observeActive(uid),
            db.redemptionDao().observeActive(uid)
        ) { habits, logs, rewards, redemptions ->
            AppSnapshot(habits, logs, rewards, redemptions)
        }.flowOn(Dispatchers.Default)
    }

    suspend fun snapshotOnce(): AppSnapshot {
        val uid = auth.uid ?: return AppSnapshot.EMPTY
        return AppSnapshot(
            db.habitDao().getActive(uid),
            db.habitLogDao().getAll(uid),
            db.rewardDao().getActive(uid),
            db.redemptionDao().getActive(uid)
        )
    }

    suspend fun getHabit(id: String): HabitEntity? = db.habitDao().getById(id)

    suspend fun logsFor(habitId: String): List<HabitLogEntity> = db.habitLogDao().getByHabit(habitId)

    suspend fun getLog(habitId: String, date: String): HabitLogEntity? =
        db.habitLogDao().getById(HabitLogEntity.idFor(habitId, date))

    suspend fun save(habit: HabitEntity) {
        val uid = auth.uid ?: return
        val toSave = habit.copy(
            uid = uid,
            deleted = false,
            updatedAt = System.currentTimeMillis(),
            syncState = SyncState.PENDING
        )
        db.habitDao().upsert(toSave)
        ReminderScheduler.apply(context, toSave)
        SyncScheduler.requestNow(context)
    }

    suspend fun delete(id: String) {
        val habit = db.habitDao().getById(id) ?: return
        db.habitDao().upsert(
            habit.copy(deleted = true, updatedAt = System.currentTimeMillis(), syncState = SyncState.PENDING)
        )
        ReminderScheduler.cancel(context, id)
        SyncScheduler.requestNow(context)
    }

    suspend fun setCompleted(habitId: String, date: String, completed: Boolean) {
        val uid = auth.uid ?: return
        val id = HabitLogEntity.idFor(habitId, date)
        val existing = db.habitLogDao().getById(id)
        val log = (existing ?: HabitLogEntity(id = id, uid = uid, habitId = habitId, date = date)).copy(
            completed = completed,
            deleted = false,
            updatedAt = System.currentTimeMillis(),
            syncState = SyncState.PENDING
        )
        db.habitLogDao().upsert(log)
        Log.d(TAG, "Log ${if (completed) "completed" else "cleared"}: $id")
        SyncScheduler.requestNow(context)
    }

    suspend fun saveNote(habitId: String, date: String, note: String) {
        val uid = auth.uid ?: return
        val id = HabitLogEntity.idFor(habitId, date)
        val existing = db.habitLogDao().getById(id)
        val log = (existing ?: HabitLogEntity(id = id, uid = uid, habitId = habitId, date = date, completed = false)).copy(
            note = note.trim(),
            deleted = false,
            updatedAt = System.currentTimeMillis(),
            syncState = SyncState.PENDING
        )
        db.habitLogDao().upsert(log)
        SyncScheduler.requestNow(context)
    }

    /** Marks every habit, log, reward and redemption of the user as deleted; the deletion then syncs. */
    suspend fun deleteAllData() {
        val uid = auth.uid ?: return
        val now = System.currentTimeMillis()
        db.habitDao().getActive(uid).forEach {
            db.habitDao().upsert(it.copy(deleted = true, updatedAt = now, syncState = SyncState.PENDING))
            ReminderScheduler.cancel(context, it.id)
        }
        db.habitLogDao().getAll(uid).forEach {
            db.habitLogDao().upsert(it.copy(deleted = true, updatedAt = now, syncState = SyncState.PENDING))
        }
        db.rewardDao().getActive(uid).forEach {
            db.rewardDao().upsert(it.copy(deleted = true, updatedAt = now, syncState = SyncState.PENDING))
        }
        db.redemptionDao().getActive(uid).forEach {
            db.redemptionDao().upsert(it.copy(deleted = true, updatedAt = now, syncState = SyncState.PENDING))
        }
        SyncScheduler.requestNow(context)
    }

    /** Imports a backup file. Rows keep their ids; existing rows are overwritten and re-uploaded. Returns the number of rows. */
    suspend fun importBackup(file: com.streakly.app.utils.BackupFile): Int {
        val uid = auth.uid ?: return 0
        val now = System.currentTimeMillis()
        var count = 0
        for (h in file.habits) {
            val existing = db.habitDao().getById(h.id)
            val saved = h.copy(uid = uid, updatedAt = now, serverVersion = existing?.serverVersion ?: 0L, syncState = SyncState.PENDING)
            db.habitDao().upsert(saved); ReminderScheduler.apply(context, saved); count++
        }
        for (l in file.logs) {
            val existing = db.habitLogDao().getById(l.id)
            db.habitLogDao().upsert(l.copy(uid = uid, updatedAt = now, serverVersion = existing?.serverVersion ?: 0L, syncState = SyncState.PENDING)); count++
        }
        for (r in file.rewards) {
            val existing = db.rewardDao().getById(r.id)
            db.rewardDao().upsert(r.copy(uid = uid, updatedAt = now, serverVersion = existing?.serverVersion ?: 0L, syncState = SyncState.PENDING)); count++
        }
        for (r in file.redemptions) {
            val existing = db.redemptionDao().getById(r.id)
            db.redemptionDao().upsert(r.copy(uid = uid, updatedAt = now, serverVersion = existing?.serverVersion ?: 0L, syncState = SyncState.PENDING)); count++
        }
        SyncScheduler.requestNow(context)
        return count
    }

    /** Removes the local cache for a user; the next sync downloads everything again. */
    suspend fun clearLocal(uid: String) {
        db.habitDao().deleteAllForUser(uid)
        db.habitLogDao().deleteAllForUser(uid)
        db.rewardDao().deleteAllForUser(uid)
        db.redemptionDao().deleteAllForUser(uid)
        db.conflictDao().deleteAllForUser(uid)
        com.streakly.app.Graph.settings.clearUser(uid)
    }

    suspend fun pendingCount(): Int {
        val uid = auth.uid ?: return 0
        return db.habitDao().countPending(uid) + db.habitLogDao().countPending(uid) +
            db.rewardDao().countPending(uid) + db.redemptionDao().countPending(uid)
    }

    companion object {
        private const val TAG = "HabitRepository"
        fun today(): String = LocalDate.now().toString()
    }
}
