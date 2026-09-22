package com.streakly.app.data.repository

import android.content.Context
import com.streakly.app.data.local.StreaklyDatabase
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.model.Habit
import com.streakly.app.data.remote.RetrofitClient
import com.streakly.app.data.remote.dto.HabitDto
import com.streakly.app.data.remote.dto.LogDto
import com.streakly.app.data.remote.dto.SyncRequest
import com.streakly.app.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class HabitRepository(private val context: Context) {

    private val db = StreaklyDatabase.get(context)

    suspend fun getHabits(): List<Habit> = withContext(Dispatchers.IO) {
        db.habitDao().getAll().map { it.toModel() }
    }

    suspend fun getHabit(id: Long): Habit? = withContext(Dispatchers.IO) {
        db.habitDao().getById(id)?.toModel()
    }

    suspend fun saveHabit(habit: Habit): Long = withContext(Dispatchers.IO) {
        val entity = habit.toEntity()
        val id = if (habit.localId == 0L) db.habitDao().upsert(entity)
        else { db.habitDao().upsert(entity.copy(id = habit.localId)); habit.localId }
        id
    }

    suspend fun deleteHabit(habit: Habit) = withContext(Dispatchers.IO) {
        val entity = habit.toEntity()
        db.habitLogDao().deleteByHabit(habit.localId)
        db.habitDao().delete(entity)
    }

    suspend fun markDone(habit: Habit, date: String = DateUtils.today()) = withContext(Dispatchers.IO) {
        db.habitLogDao().upsert(
            HabitLogEntity(
                habitId = habit.localId,
                date = date,
                completed = true,
                note = "",
                pendingSync = true
            )
        )
    }

    suspend fun getCompletionDates(habitId: Long): List<String> = withContext(Dispatchers.IO) {
        db.habitLogDao().getByHabit(habitId).filter { it.completed }.map { it.date }
    }

    suspend fun clearAllLocalData() = withContext(Dispatchers.IO) {
        db.habitLogDao().deleteAll()
        db.habitDao().deleteAll()
    }

    suspend fun pushPending(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val pendingHabits = db.habitDao().getPending()
            val pendingLogs = db.habitLogDao().getPending()
            if (pendingHabits.isEmpty() && pendingLogs.isEmpty()) return@withContext true

            val remoteMap = mutableMapOf<Long, String>()
            for (h in pendingHabits) {
                val rid = h.remoteId ?: UUID.randomUUID().toString()
                remoteMap[h.id] = rid
                db.habitDao().upsert(h.copy(remoteId = rid))
            }

            val request = SyncRequest(
                habits = pendingHabits.map {
                    HabitDto(
                        remoteId = it.remoteId ?: remoteMap[it.id] ?: "",
                        name = it.name, description = it.description, notes = it.notes,
                        color = it.color, icon = it.icon, frequencyType = it.frequencyType,
                        daysOfWeek = it.daysOfWeek, interval = it.interval,
                        reminderHour = it.reminderHour, reminderMinute = it.reminderMinute,
                        createdAt = it.createdAt
                    )
                },
                logs = pendingLogs.map {
                    LogDto(
                        habitRemoteId = remoteMap[it.habitId]
                            ?: db.habitDao().getById(it.habitId)?.remoteId ?: "",
                        date = it.date, completed = it.completed, note = it.note
                    )
                }
            )
            RetrofitClient.api.sync(request)
            true
        } catch (e: Exception) { false }
    }

    suspend fun refreshFromServer(): Boolean = withContext(Dispatchers.IO) {
        try {
            val resp = RetrofitClient.api.sync(
                SyncRequest(habits = emptyList(), logs = emptyList())
            )
            resp.serverTime
            true
        } catch (e: Exception) { false }
    }

    private fun HabitEntity.toModel() = Habit(
        localId = id, remoteId = remoteId, name = name, description = description,
        notes = notes, color = color, icon = icon, frequencyType = frequencyType,
        daysOfWeek = daysOfWeek, interval = interval, reminderHour = reminderHour,
        reminderMinute = reminderMinute, createdAt = createdAt,
        updatedAt = updatedAt, pendingSync = pendingSync
    )

    private fun Habit.toEntity() = HabitEntity(
        id = localId, remoteId = remoteId, name = name, description = description,
        notes = notes, color = color, icon = icon, frequencyType = frequencyType,
        daysOfWeek = daysOfWeek, interval = interval, reminderHour = reminderHour,
        reminderMinute = reminderMinute,
        createdAt = if (createdAt.isEmpty()) DateUtils.today() else createdAt,
        updatedAt = System.currentTimeMillis(), pendingSync = true
    )
}
