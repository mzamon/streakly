package com.streakly.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.streakly.app.data.local.dao.*
import com.streakly.app.data.local.entity.*

@Database(
    entities = [HabitEntity::class, HabitLogEntity::class, RewardEntity::class,
        RedemptionEntity::class, UserEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StreaklyDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitLogDao(): HabitLogDao
    abstract fun rewardDao(): RewardDao
    abstract fun redemptionDao(): RedemptionDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: StreaklyDatabase? = null
        fun get(context: Context): StreaklyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, StreaklyDatabase::class.java, "streakly.db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
    }
}
