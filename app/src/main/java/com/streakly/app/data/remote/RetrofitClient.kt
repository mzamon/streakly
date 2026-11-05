package com.streakly.app.data.remote

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.streakly.app.BuildConfig
import com.streakly.app.data.FirebaseConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** Attaches the signed-in user's Firebase ID token so the API can verify who is calling. */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val user = if (FirebaseConfig.isReady()) FirebaseAuth.getInstance().currentUser else null
        var token = tokenFor(user, false)
        var response = chain.proceed(withToken(chain, token))
        if (response.code == 401 && user != null) {
            // The cached token may have expired: force a refresh and retry exactly once.
            response.close()
            token = tokenFor(user, true)
            response = chain.proceed(withToken(chain, token))
        }
        return response
    }

    private fun tokenFor(user: com.google.firebase.auth.FirebaseUser?, force: Boolean): String? {
        if (user == null) return null
        return try {
            Tasks.await(user.getIdToken(force), 15, TimeUnit.SECONDS).token
        } catch (e: Exception) {
            Log.w(TAG, "Could not obtain ID token: ${e.message}")
            null
        }
    }

    private fun withToken(chain: Interceptor.Chain, token: String?): okhttp3.Request {
        val builder = chain.request().newBuilder()
        if (token != null) builder.header("Authorization", "Bearer $token")
        return builder.build()
    }

    companion object {
        private const val TAG = "AuthInterceptor"
    }
}

object RetrofitClient {
    private fun baseUrl(): String {
        val url = BuildConfig.API_BASE_URL.trim()
        return if (url.endsWith("/")) url else "$url/"
    }

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            // Free hosting tiers can take ~30 s to wake up, so timeouts are generous.
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        }
        builder.build()
    }

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
