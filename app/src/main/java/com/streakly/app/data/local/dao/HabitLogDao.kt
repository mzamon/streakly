package com.streakly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streakly.app.data.local.entity.HabitLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
/** Room access for completion logs, including records waiting for remote sync. */
interface HabitLogDao {
    @Query("SELECT * FROM habit_logs WHERE uid = :uid AND deleted = 0")
    fun observeAll(uid: String): Flow<List<HabitLogEntity>>

    @Query("SELECT * FROM habit_logs WHERE uid = :uid AND deleted = 0")
    suspend fun getAll(uid: String): List<HabitLogEntity>

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId AND deleted = 0 ORDER BY date DESC")
    suspend fun getByHabit(habitId: String): List<HabitLogEntity>

    @Query("SELECT * FROM habit_logs WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): HabitLogEntity?

    @Query("SELECT * FROM habit_logs WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun getPending(uid: String): List<HabitLogEntity>

    @Query("SELECT COUNT(*) FROM habit_logs WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun countPending(uid: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(log: HabitLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(logs: List<HabitLogEntity>)

    @Query("DELETE FROM habit_logs WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}
