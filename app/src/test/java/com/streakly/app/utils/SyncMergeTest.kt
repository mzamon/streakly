package com.streakly.app.utils

import com.streakly.app.data.local.entity.SyncState
import org.junit.Assert.assertEquals
import org.junit.Test

class SyncMergeTest {
    @Test fun `unknown row is taken from the server`() {
        assertEquals(MergeDecision.TAKE_REMOTE, SyncMerge.decide(null, 0L, 100L))
    }

    @Test fun `synced local row is overwritten by the server copy`() {
        assertEquals(MergeDecision.TAKE_REMOTE, SyncMerge.decide(SyncState.SYNCED, 50L, 100L))
    }

    @Test fun `echo of our own push marks the row synced`() {
        assertEquals(MergeDecision.MARK_SYNCED, SyncMerge.decide(SyncState.PENDING, 100L, 100L))
    }

    @Test fun `newer local edit is never overwritten`() {
        assertEquals(MergeDecision.KEEP_LOCAL, SyncMerge.decide(SyncState.PENDING, 200L, 100L))
    }

    @Test fun `different pending edit is kept so the server can flag the conflict`() {
        assertEquals(MergeDecision.KEEP_LOCAL, SyncMerge.decide(SyncState.PENDING, 50L, 100L))
    }

    @Test fun `rows waiting for a conflict decision are left alone`() {
        assertEquals(MergeDecision.KEEP_LOCAL, SyncMerge.decide(SyncState.CONFLICT, 50L, 100L))
    }
}
