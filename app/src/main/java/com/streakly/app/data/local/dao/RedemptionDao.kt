package com.streakly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streakly.app.data.local.entity.RedemptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RedemptionDao {
    @Query("SELECT * FROM redemptions WHERE uid = :uid AND deleted = 0 ORDER BY date DESC, updatedAt DESC")
    fun observeActive(uid: String): Flow<List<RedemptionEntity>>

    @Query("SELECT * FROM redemptions WHERE uid = :uid AND deleted = 0 ORDER BY date DESC, updatedAt DESC")
    suspend fun getActive(uid: String): List<RedemptionEntity>

    @Query("SELECT * FROM redemptions WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): RedemptionEntity?

    @Query("SELECT * FROM redemptions WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun getPending(uid: String): List<RedemptionEntity>

    @Query("SELECT COUNT(*) FROM redemptions WHERE uid = :uid AND syncState = 'PENDING'")
    suspend fun countPending(uid: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(redemption: RedemptionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(redemptions: List<RedemptionEntity>)

    @Query("DELETE FROM redemptions WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}
