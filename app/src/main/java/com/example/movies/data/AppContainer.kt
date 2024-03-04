package com.example.movies.data

import com.example.movies.network.MovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val movieRepository: MovieRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://api.themoviedb.org/3/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService:: class.java)
    }

    override val movieRepository: MovieRepository by lazy {
        NetworkMovieRepository(retrofitService)
    }
}