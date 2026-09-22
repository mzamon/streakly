package com.streakly.app.data.local.dao

import androidx.room.*
import com.streakly.app.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity): Long

    @Query("UPDATE users SET name = :name WHERE email = :email")
    suspend fun updateName(email: String, name: String)

    @Query("UPDATE users SET salt = :salt, passwordHash = :hash WHERE email = :email")
    suspend fun updatePassword(email: String, salt: String, hash: String)

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun countByEmail(email: String): Int

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteByEmail(email: String)
}
