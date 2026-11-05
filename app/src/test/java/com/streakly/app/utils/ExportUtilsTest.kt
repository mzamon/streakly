package com.streakly.app.utils

import com.streakly.app.data.local.entity.HabitEntity
import com.streakly.app.data.local.entity.HabitLogEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExportUtilsTest {
    private val habit = HabitEntity(id = "h1", uid = "u1", name = "Read, daily", createdAt = "2026-10-01")

    @Test fun `csv fields are quoted when needed`() {
        assertEquals("plain", ExportUtils.csvField("plain"))
        assertEquals("\"a,b\"", ExportUtils.csvField("a,b"))
        assertEquals("\"say \"\"hi\"\"\"", ExportUtils.csvField("say \"hi\""))
    }

    @Test fun `csv export lists logs by habit name`() {
        val log = HabitLogEntity(id = "h1|2026-10-02", uid = "u1", habitId = "h1", date = "2026-10-02", completed = true, note = "ok, good")
        val csv = ExportUtils.buildCsv(listOf(habit), listOf(log))
        val lines = csv.trim().split("\n")
        assertEquals("habit,date,completed,note", lines[0])
        assertEquals("\"Read, daily\",2026-10-02,yes,\"ok, good\"", lines[1])
    }

    @Test fun `backup round trips through json`() {
        val json = ExportUtils.buildJson(BackupFile(exportedAt = 5L, habits = listOf(habit)))
        val parsed = ExportUtils.parseBackup(json)
        assertNotNull(parsed)
        assertEquals(1, parsed!!.habits.size)
        assertEquals("Read, daily", parsed.habits[0].name)
        assertTrue(parsed.exportedAt == 5L)
    }

    @Test fun `invalid backups are rejected`() {
        assertNull(ExportUtils.parseBackup("not json at all"))
        assertNull(ExportUtils.parseBackup("{}"))
        assertNull(ExportUtils.parseBackup("{\"app\":\"other\",\"version\":1}"))
    }
}
