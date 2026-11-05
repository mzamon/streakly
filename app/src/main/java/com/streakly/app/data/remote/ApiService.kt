package com.streakly.app.data.remote

import com.streakly.app.data.remote.dto.DeviceRequest
import com.streakly.app.data.remote.dto.HealthResponse
import com.streakly.app.data.remote.dto.MessageResponse
import com.streakly.app.data.remote.dto.SyncRequest
import com.streakly.app.data.remote.dto.SyncResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

/** Streakly REST API (Node.js / Express, data stored in Cloud Firestore). */
interface ApiService {
    @GET("health")
    suspend fun health(): HealthResponse

    @POST("api/sync")
    suspend fun sync(@Body request: SyncRequest): SyncResponse

    @POST("api/devices")
    suspend fun registerDevice(@Body request: DeviceRequest): MessageResponse

    @HTTP(method = "DELETE", path = "api/devices", hasBody = true)
    suspend fun unregisterDevice(@Body request: DeviceRequest): MessageResponse

    @POST("api/push/test")
    suspend fun testPush(): MessageResponse

    @DELETE("api/account")
    suspend fun deleteAccount(): MessageResponse
}
