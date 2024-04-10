package com.example.movies.screens.trending

import android.text.Editable.Factory
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.AuthRepository
import com.example.movies.data.MovieApiRepository
import com.example.movies.data.MovieLikeRepository
import com.example.movies.data.MovieStorageRepository
import com.example.movies.model.Movie
import com.example.movies.screens.common.MovieViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrendingViewModel(
    private val _movieApiRepository: MovieApiRepository,
    private val _movieStorageRepository: MovieStorageRepository,
    private val _authRepository: AuthRepository,
    private val _movieLikeRepository: MovieLikeRepository
): MovieViewModel(
    _authRepository,
    _movieLikeRepository
) {
    init {
        getTrendingMovies()
    }

    private suspend fun clearMovies() {
        _movieStorageRepository.clearAllMovies()
    }

    // Note that this will not insert duplicate entries because
    // movies with identical ids will not insert
    private suspend fun saveMovies() {
        movieUiState.value.movies.forEach {
                movie ->
            _movieStorageRepository.insertMovie(movie)
        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            try {
                val listResult: List<Movie> = _movieApiRepository.getMovies()

                movieUiState.update {
                        currentState ->
                    currentState.copy(movies = listResult)
                }
                clearMovies()
                saveMovies()
            } catch (e: Exception) {
                Log.w("OFFLINE", "Service offline")
                val listResult = _movieStorageRepository.getAllMovies()
                movieUiState.update {
                        currentState ->
                    currentState.copy(movies = listResult)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val movieApiRepository = application.container.movieApiRepository
                val movieStorageRepository = application.container.movieStorageRepository
                TrendingViewModel(
                    _movieApiRepository = movieApiRepository,
                    _movieStorageRepository = movieStorageRepository,
                    _authRepository = application.container.authRepository,
                    _movieLikeRepository = application.container.movieLikeRepository
                )
            }
        }
    }
}