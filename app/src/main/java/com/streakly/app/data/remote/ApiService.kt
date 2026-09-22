package com.streakly.app.data.remote

import com.streakly.app.data.remote.dto.SyncRequest
import com.streakly.app.data.remote.dto.SyncResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("health")
    suspend fun health(): Map<String, String>

    @POST("sync")
    suspend fun sync(@Body request: SyncRequest): SyncResponse
}
