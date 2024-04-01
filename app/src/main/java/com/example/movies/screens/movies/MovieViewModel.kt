package com.example.movies.screens.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.AuthRepository
import com.example.movies.data.MovieApiRepository
import com.example.movies.data.MovieStorageRepository
import com.example.movies.model.Movie
import com.example.movies.model.MovieUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.logging.Logger

data class MovieUiState (
    val movies: List<Movie> = mutableListOf()
)

class MovieViewModel(
    private val movieApiRepository: MovieApiRepository,
    private val movieStorageRepository: MovieStorageRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    // private variable
    private val _movieUiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    init{
        getTrendingMovies()
    }

    fun getUser() {
        val user: MovieUser = authRepository.getCurrentUser()
        Log.d("CURRENT USER", user.toString())
    }

    private suspend fun clearMovies() {
        movieStorageRepository.clearAllMovies()
    }

    // Note that this will not insert duplicate entries because
    // movies with identical ids will not insert
    private suspend fun saveMovies() {
        _movieUiState.value.movies.forEach {
            movie ->
            movieStorageRepository.insertMovie(movie)
        }
    }


    private fun getTrendingMovies() {
        viewModelScope.launch {
            try {
                var listResult: List<Movie> = movieApiRepository.getMovies()

                _movieUiState.update {
                        currentState ->
                    currentState.copy(movies = listResult)
                }
                clearMovies()
                saveMovies()
            } catch (e: Exception) {
                Log.w("OFFLINE", "Service offline")
                var listResult = movieStorageRepository.getAllMovies()
                _movieUiState.update {
                        currentState ->
                    currentState.copy(movies = listResult)
              }
            }
        }
    }

    /*
    "Android framework does not allow ViewModel to be passed values in
    the constructor when created. Implement ViewModelProvider.Factory

    Companion Object is a class object similar to a static prop but also lazy initialized
     */

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                val movieApiRepository = application.container.movieApiRepository
                val movieStorageRepository = application.container.movieStorageRepository
                MovieViewModel(
                    movieApiRepository = movieApiRepository,
                    movieStorageRepository = movieStorageRepository,
                    authRepository = application.container.authRepository
                )
            }
        }
    }
}