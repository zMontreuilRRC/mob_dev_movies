package com.example.movies.network

import com.example.movies.model.MovieData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val TRENDING_MOVIES_ENDPOINT = "trending/movie/day"
private const val SEARCH_MOVIES_ENDPOINT = "search/movie"
private const val KEY_HEADER = "29739bd57dd03371feec6e6d7a1d45a3"



interface MovieApiService {
    @GET(TRENDING_MOVIES_ENDPOINT)
    suspend fun getMovies(
        @Query("api_key") key: String = KEY_HEADER
    ): MovieData

    @GET(SEARCH_MOVIES_ENDPOINT)
    suspend fun searchMovies(
        @Query("api_key") key: String = KEY_HEADER,
        @Query("query") searchTitle: String
    ):MovieData
}
