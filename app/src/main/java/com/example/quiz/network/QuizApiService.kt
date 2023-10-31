package com.example.quiz.network

import android.content.Context
import com.example.quiz.Data.CategoryPageUtils
import com.example.quiz.Data.QuizData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val currentCategory: CategoryPageUtils? = null

private val URL = "https://opentdb.com"

private val json = Json{
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(URL)
    .build()

interface QuizApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 10,
        @Query("type") type: String = "multiple",
        @Query("category") category: Int
    ) : QuizData
}

object QuizApi {
    val retrofitService : QuizApiService by lazy {
        retrofit.create(QuizApiService::class.java)
    }
}

object QuizRepository {
    private const val PREFS_NAME = "quiz_prefs"
    private const val LAST_FETCH_TIMESTAMP = "last_fetch_timestamp"

    fun shouldFetchNewQuestions(context: Context, category: Int, refreshInterval: Long): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lastFetchTimestamp = sharedPreferences.getLong(LAST_FETCH_TIMESTAMP, 0)
        val currentTimestamp = System.currentTimeMillis()
        return currentTimestamp - lastFetchTimestamp >= refreshInterval
    }

    fun saveFetchTimestamp(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentTimestamp = System.currentTimeMillis()
        sharedPreferences.edit().putLong(LAST_FETCH_TIMESTAMP, currentTimestamp).apply()
    }
}