package com.example.movies.data

import com.example.movies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieStorageRepository {
    // flow obviates suspend
    suspend fun getAllMovies(): List<Movie>
    suspend fun insertMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
    suspend fun updateMovie(movie: Movie)
    suspend fun clearAllMovies()
}

class OfflineMovieStorageRepository(private val movieDao: MovieDao): MovieStorageRepository {
    override suspend fun getAllMovies(): List<Movie> {
        return movieDao.getAllMovies()
    }

    override suspend fun clearAllMovies() {
        movieDao.clearAllMovies()
    }

    override suspend fun insertMovie(movie: Movie) {
        movieDao.insert(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.delete(movie)
    }

    override suspend fun updateMovie(movie: Movie) {
        movieDao.update(movie)
    }
}