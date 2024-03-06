package com.example.movies.data

import com.example.movies.network.MovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import android.content.Context

interface AppContainer {
    val movieApiRepository: MovieApiRepository
    val movieStorageRepository: MovieStorageRepository
}

class DefaultAppContainer (private val context: Context): AppContainer {
    // add retrofit db
    private val baseUrl = "https://api.themoviedb.org/3/"

    private val configuredJson = Json {
        isLenient = true
        coerceInputValues = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(configuredJson.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService:: class.java)
    }

    override val movieApiRepository: MovieApiRepository by lazy {
        NetworkMovieApiRepository(retrofitService)
    }

    // add room db
    override val movieStorageRepository: MovieStorageRepository by lazy {
        OfflineMovieStorageRepository(MovieDatabase.getDatabase(context).movieDao())
    }
}