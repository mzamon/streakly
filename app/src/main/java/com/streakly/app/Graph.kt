package com.streakly.app

import android.app.Application
import com.streakly.app.data.FirebaseConfig
import com.streakly.app.data.local.StreaklyDatabase
import com.streakly.app.data.remote.RetrofitClient
import com.streakly.app.data.repository.AuthRepository
import com.streakly.app.data.repository.HabitRepository
import com.streakly.app.data.repository.RewardRepository
import com.streakly.app.data.repository.SyncRepository
import com.streakly.app.data.session.SessionManager

/** Tiny service locator: one instance of every repository for the whole process. */
object Graph {
    private lateinit var app: Application

    fun init(application: Application) {
        app = application
        FirebaseConfig.init(application)
    }

    val context: Application get() = app
    val db: StreaklyDatabase by lazy { StreaklyDatabase.get(app) }
    val settings: SessionManager by lazy { SessionManager(app) }
    val auth: AuthRepository by lazy { AuthRepository(app) }
    val habits: HabitRepository by lazy { HabitRepository(app, db, auth) }
    val rewards: RewardRepository by lazy { RewardRepository(app, db, auth, habits) }
    val sync: SyncRepository by lazy { SyncRepository(app, db, auth, settings, RetrofitClient.api) }
}
