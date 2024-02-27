package com.example.movies.data

import com.example.movies.model.MovieData
import com.example.movies.network.MovieApiService

interface MovieRepository {
    suspend fun getMovieData(): MovieData
}

class NetworkMovieRepository(
    // pass in any type of API service (such as retrofit or fake)
    private val movieApiService: MovieApiService
): MovieRepository {
    override suspend fun getMovieData(): MovieData {
        return movieApiService.getMovies()
    }
}

