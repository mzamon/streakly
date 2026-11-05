package com.streakly.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streakly.app.data.local.entity.ConflictEntity
import kotlinx.coroutines.flow.Flow

@Dao
/** Stores unresolved local/cloud edits until the user chooses a version. */
interface ConflictDao {
    @Query("SELECT * FROM conflicts WHERE uid = :uid ORDER BY detectedAt DESC")
    fun observeAll(uid: String): Flow<List<ConflictEntity>>

    @Query("SELECT * FROM conflicts WHERE uid = :uid ORDER BY detectedAt DESC")
    suspend fun getAll(uid: String): List<ConflictEntity>

    @Query("SELECT * FROM conflicts WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ConflictEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(conflict: ConflictEntity)

    @Query("DELETE FROM conflicts WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM conflicts WHERE uid = :uid")
    suspend fun deleteAllForUser(uid: String)
}
