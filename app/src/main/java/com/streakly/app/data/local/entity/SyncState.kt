package com.streakly.app.data.local.entity

/** Synchronisation state stored with every row that is mirrored to the REST API. */
object SyncState {
    const val PENDING = "PENDING"
    const val SYNCED = "SYNCED"
    const val CONFLICT = "CONFLICT"
}
