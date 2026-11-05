package com.streakly.app.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import com.streakly.app.data.local.entity.RedemptionEntity
import com.streakly.app.data.local.entity.RewardEntity

/** Full backup file written by "Export JSON" and read by "Import backup". */
data class BackupFile(
    val app: String = "streakly",
    val version: Int = 1,
    val exportedAt: Long = 0L,
    val habits: List<HabitEntity> = emptyList(),
    val logs: List<HabitLogEntity> = emptyList(),
    val rewards: List<RewardEntity> = emptyList(),
    val redemptions: List<RedemptionEntity> = emptyList()
)

object ExportUtils {
    private val gson = Gson()

    /** Quotes a CSV field and doubles any quotes inside it. */
    fun csvField(value: String): String {
        val needsQuotes = value.any { it == ',' || it == '"' || it == '\n' || it == '\r' }
        val escaped = value.replace("\"", "\"\"")
        return if (needsQuotes) "\"$escaped\"" else escaped
    }

    fun buildCsv(habits: List<HabitEntity>, logs: List<HabitLogEntity>): String {
        val names = habits.associate { it.id to it.name }
        val sb = StringBuilder("habit,date,completed,note\n")
        logs.filter { names.containsKey(it.habitId) }
            .sortedWith(compareBy({ names[it.habitId] ?: "" }, { it.date }))
            .forEach {
                sb.append(csvField(names[it.habitId] ?: "")).append(',')
                    .append(it.date).append(',')
                    .append(if (it.completed) "yes" else "no").append(',')
                    .append(csvField(it.note)).append('\n')
            }
        return sb.toString()
    }

    fun buildJson(backup: BackupFile): String = gson.toJson(backup)

    /** Returns the parsed backup, or null when the text is not a valid Streakly backup. */
    fun parseBackup(json: String): BackupFile? {
        return try {
        val root = JsonParser().parse(json)
        if (!root.isJsonObject ||
            !root.asJsonObject.has("app") ||
            !root.asJsonObject.has("version")
        ) {
            return null
        }
        val file = gson.fromJson(json, BackupFile::class.java)
        if (file != null && file.app == "streakly" && file.version == 1) file else null
        } catch (e: JsonSyntaxException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}
