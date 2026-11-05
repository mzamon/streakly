package com.streakly.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/** The cloud copy of a row that was edited on two devices. The user chooses which version to keep. */
@Entity(tableName = "conflicts", indices = [Index("uid")])
data class ConflictEntity(
    @PrimaryKey val id: String,          // "<type>:<entityId>"
    val uid: String,
    val type: String,                    // habit | log | reward | redemption
    val entityId: String,
    val serverJson: String,
    val detectedAt: Long = System.currentTimeMillis()
)
