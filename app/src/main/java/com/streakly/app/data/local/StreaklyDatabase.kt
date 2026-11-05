package com.streakly.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.streakly.app.data.local.dao.ConflictDao
import com.streakly.app.data.local.dao.HabitDao
import com.streakly.app.data.local.dao.HabitLogDao
import com.streakly.app.data.local.dao.RedemptionDao
import com.streakly.app.data.local.dao.RewardDao
import com.streakly.app.data.local.entity.ConflictEntity
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity

/**
 * Room database: the single source of truth for the UI. Every screen reads from here, so the app keeps
 * working offline; [com.streakly.app.data.repository.SyncRepository] mirrors changes with the REST API.
 */
@Database(
    entities = [
        HabitEntity::class, HabitLogEntity::class, RewardEntity::class,
        RedemptionEntity::class, ConflictEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class StreaklyDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitLogDao(): HabitLogDao
    abstract fun rewardDao(): RewardDao
    abstract fun redemptionDao(): RedemptionDao
    abstract fun conflictDao(): ConflictDao

    companion object {
        @Volatile
        private var INSTANCE: StreaklyDatabase? = null

        fun get(context: Context): StreaklyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StreaklyDatabase::class.java,
                    "streakly.db"
                )
                    // Version 1 was a local-only prototype schema; its data is replaced by the cloud-synced model.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
