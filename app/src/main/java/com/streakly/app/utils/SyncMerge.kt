package com.streakly.app.utils

import com.streakly.app.data.local.entity.SyncState

/** What to do when the server reports a row that already exists on this device. */
enum class MergeDecision { TAKE_REMOTE, KEEP_LOCAL, MARK_SYNCED }

object SyncMerge {
    /**
     * @param localState sync state of the local row, or null when the row does not exist locally
     * @param localUpdatedAt local edit time (ignored when the row is absent)
     * @param remoteUpdatedAt edit time carried by the server copy
     */
    fun decide(localState: String?, localUpdatedAt: Long, remoteUpdatedAt: Long): MergeDecision = when {
        localState == null -> MergeDecision.TAKE_REMOTE
        // Local edit not uploaded yet: the echo of our own push confirms it, anything else must not overwrite it.
        localState == SyncState.PENDING ->
            if (localUpdatedAt == remoteUpdatedAt) MergeDecision.MARK_SYNCED else MergeDecision.KEEP_LOCAL
        // The user still has to choose between the two versions.
        localState == SyncState.CONFLICT -> MergeDecision.KEEP_LOCAL
        else -> MergeDecision.TAKE_REMOTE
    }
}
