package com.streakly.app.data.local.dao

import androidx.room.*
import com.streakly.app.data.local.entity.RewardEntity

@Dao
interface RewardDao {
    @Query("SELECT * FROM rewards ORDER BY id DESC")
    suspend fun getAll(): List<RewardEntity>

    @Query("SELECT * FROM rewards WHERE id = :id")
    suspend fun getById(id: Long): RewardEntity?

    @Query("SELECT * FROM rewards WHERE pendingSync = 1")
    suspend fun getPending(): List<RewardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(reward: RewardEntity): Long

    @Delete
    suspend fun delete(reward: RewardEntity)

    @Query("DELETE FROM rewards")
    suspend fun deleteAll()
}
