package com.streakly.app.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.streakly.app.Graph
import com.streakly.app.R
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.model.schedule
import com.streakly.app.push.AppIntents
import com.streakly.app.push.NotificationHelper
import com.streakly.app.utils.ReminderTime
import com.streakly.app.utils.StreakCalculator
import java.time.LocalDate
import java.util.concurrent.TimeUnit

/**
 * Local reminders. Each reminder is a one-time work request that reschedules itself for the next day, so it keeps
 * running after reboots without exact-alarm permissions. [KEY_ID] is a habit id or "daily".
 */
class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val key = inputData.getString(KEY_ID) ?: return Result.success()
        val uid = Graph.auth.uid ?: return Result.success()
        val settings = Graph.settings
        val today = LocalDate.now()

        if (key == DAILY) {
            if (!settings.dailyReminderEnabled) return Result.success()
            val habits = Graph.db.habitDao().getActive(uid)
            val open = habits.count { isOpenToday(it, today) }
            if (open > 0 && settings.pushEnabled) {
                NotificationHelper.show(
                    applicationContext, NotificationHelper.CH_REMINDERS, 1000,
                    applicationContext.getString(R.string.daily_reminder_title),
                    applicationContext.resources.getQuantityString(R.plurals.daily_reminder_body, open, open),
                    AppIntents.DEST_HOME
                )
            }
            ReminderScheduler.schedule(applicationContext, DAILY, settings.dailyReminderHour, settings.dailyReminderMinute)
            return Result.success()
        }

        val habit = Graph.db.habitDao().getById(key)
        if (habit == null || habit.deleted || habit.uid != uid || habit.reminderHour < 0 || !settings.habitRemindersEnabled) {
            return Result.success()
        }
        if (isOpenToday(habit, today) && settings.pushEnabled) {
            NotificationHelper.show(
                applicationContext, NotificationHelper.CH_REMINDERS, 1000 + (habit.id.hashCode() and 0xFFFF),
                applicationContext.getString(R.string.habit_reminder_title, habit.name),
                applicationContext.getString(R.string.habit_reminder_body),
                AppIntents.DEST_HABIT, habit.id
            )
        }
        ReminderScheduler.schedule(applicationContext, habit.id, habit.reminderHour, habit.reminderMinute)
        Log.d(TAG, "Reminder handled for ${habit.id}")
        return Result.success()
    }

    private suspend fun isOpenToday(habit: HabitEntity, today: LocalDate): Boolean {
        if (!StreakCalculator.isDueOn(habit.schedule(), today, today)) return false
        val log = Graph.db.habitLogDao().getById(HabitLogEntity.idFor(habit.id, today.toString()))
        return log == null || !log.completed
    }

    companion object {
        const val KEY_ID = "key"
        const val DAILY = "daily"
        private const val TAG = "ReminderWorker"
    }
}

object ReminderScheduler {
    private fun unique(key: String) = "reminder_$key"

    fun schedule(context: Context, key: String, hour: Int, minute: Int) {
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(ReminderTime.delayUntil(hour, minute), TimeUnit.MILLISECONDS)
            .setInputData(Data.Builder().putString(ReminderWorker.KEY_ID, key).build())
            .build()
        WorkManager.getInstance(context.applicationContext)
            .enqueueUniqueWork(unique(key), ExistingWorkPolicy.REPLACE, request)
    }

    fun cancel(context: Context, key: String) {
        WorkManager.getInstance(context.applicationContext).cancelUniqueWork(unique(key))
    }

    /** Applies the habit's reminder (or removes it when none is set). */
    fun apply(context: Context, habit: HabitEntity) {
        if (habit.reminderHour >= 0 && !habit.deleted && Graph.settings.habitRemindersEnabled) {
            schedule(context, habit.id, habit.reminderHour, habit.reminderMinute)
        } else {
            cancel(context, habit.id)
        }
    }

    suspend fun rescheduleAll(context: Context) {
        val uid = Graph.auth.uid ?: return
        Graph.db.habitDao().getActive(uid).forEach { apply(context, it) }
        val s = Graph.settings
        if (s.dailyReminderEnabled) schedule(context, ReminderWorker.DAILY, s.dailyReminderHour, s.dailyReminderMinute)
        else cancel(context, ReminderWorker.DAILY)
    }
}
