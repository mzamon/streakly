package com.streakly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streakly.app.data.local.entity.RewardEntity
import kotlinx.coroutines.flow.Flow

@Dao
/** Room access for the user's active rewards and their sync state. */
interface RewardDao {
    @Query("SELECT * FROM rewards WHERE uid = :uid AND deleted = 0 ORDER BY cost ASC, name ASC")
    fun observeActive(uid: String): Flow<List<RewardEntity>>

    @Query("SELECT * FROM rewards WHERE uid = :uid AND deleted = 0 ORDER BY cost ASC, name ASC")
    suspend fun getActive(uid: String): List<RewardEntity>

    @Query("SELECT * FROM rewards WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): RewardEntity?

    @Query("SELECT * FROM rewards WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun getPending(uid: String): List<RewardEntity>

    @Query("SELECT COUNT(*) FROM rewards WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun countPending(uid: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(reward: RewardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(rewards: List<RewardEntity>)

    @Query("DELETE FROM rewards WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}
