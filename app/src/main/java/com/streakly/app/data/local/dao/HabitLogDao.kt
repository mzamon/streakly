package com.streakly.app.data.local.dao

import androidx.room.*
import com.streakly.app.data.local.entity.HabitLogEntity

@Dao
interface HabitLogDao {
    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId ORDER BY date DESC")
    suspend fun getByHabit(habitId: Long): List<HabitLogEntity>

    @Query("SELECT * FROM habit_logs WHERE pendingSync = 1")
    suspend fun getPending(): List<HabitLogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(log: HabitLogEntity): Long

    @Query("DELETE FROM habit_logs WHERE habitId = :habitId")
    suspend fun deleteByHabit(habitId: Long)

    @Query("DELETE FROM habit_logs")
    suspend fun deleteAll()

    @Query("UPDATE habit_logs SET pendingSync = 0 WHERE id = :id")
    suspend fun markSynced(id: Long)
}
