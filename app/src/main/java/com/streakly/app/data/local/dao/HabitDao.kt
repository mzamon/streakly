package com.streakly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streakly.app.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
/** Room access for active, pending, and user-owned habit records. */
interface HabitDao {
    @Query("SELECT * FROM habits WHERE uid = :uid AND deleted = 0 ORDER BY createdAt ASC, name ASC")
    fun observeActive(uid: String): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE uid = :uid AND deleted = 0 ORDER BY createdAt ASC, name ASC")
    suspend fun getActive(uid: String): List<HabitEntity>

    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): HabitEntity?

    @Query("SELECT * FROM habits WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun getPending(uid: String): List<HabitEntity>

    @Query("SELECT COUNT(*) FROM habits WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun countPending(uid: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(habits: List<HabitEntity>)

    @Query("DELETE FROM habits WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}
