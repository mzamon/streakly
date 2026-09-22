package com.streakly.app.data.local.dao

import androidx.room.*
import com.streakly.app.data.local.entity.RedemptionEntity

@Dao
interface RedemptionDao {
    @Query("SELECT * FROM redemptions ORDER BY id DESC")
    suspend fun getAll(): List<RedemptionEntity>

    @Query("SELECT COALESCE(SUM(cost),0) FROM redemptions")
    suspend fun totalSpent(): Int

    @Query("SELECT * FROM redemptions WHERE pendingSync = 1")
    suspend fun getPending(): List<RedemptionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(redemption: RedemptionEntity): Long

    @Query("DELETE FROM redemptions")
    suspend fun deleteAll()
}
