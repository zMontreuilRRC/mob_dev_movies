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
import com.example.movies.data.MovieLikeRepository
import com.example.movies.data.MovieStorageRepository
import com.example.movies.model.Movie
import com.example.movies.model.MovieLike
import com.example.movies.model.MovieUser
import com.example.movies.screens.common.MovieVmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieUiState (
    val movies: List<Movie> = mutableListOf(),
    val movieLikes: List<MovieLike> = mutableListOf()
)

class MovieViewModel(
    private val _movieApiRepository: MovieApiRepository,
    private val _movieStorageRepository: MovieStorageRepository,
    private val _authRepository: AuthRepository,
    private val _movieLikeRepository: MovieLikeRepository
): ViewModel() {
    // private variable
    private val _movieUiState = MutableStateFlow(MovieVmUiState())
    val uiState: StateFlow<MovieVmUiState> = _movieUiState.asStateFlow()

    init{
        getTrendingMovies()
    }

    // TODO: Add movie like functionality to main screen, extract into common movieVM ancestor
    private fun getMovieLikes(userId: String) {
        try {
            _movieLikeRepository.getLikedMovies(userId) {
                val user:MovieUser = _authRepository.getCurrentUser()
                _movieLikeRepository.getLikedMovies(userId) {
                    result ->
                    Log.i(user.id, result.toString())
                    // TODO : populate list of likes for rendering in movies
                    // Invoke on login
                }
            }
        } catch(e: Exception) {
            Log.w("GET:MOVIELIKES", e.toString())
        }
    }

    fun postMovieLike(movieId: String) {
        // TODO: post movie with user id and movie id
    }

    private suspend fun clearMovies() {
        _movieStorageRepository.clearAllMovies()
    }

    // Note that this will not insert duplicate entries because
    // movies with identical ids will not insert
    private suspend fun saveMovies() {
        _movieUiState.value.movies.forEach {
            movie ->
            _movieStorageRepository.insertMovie(movie)
        }
    }


    private fun getTrendingMovies() {
        viewModelScope.launch {
            try {
                val listResult: List<Movie> = _movieApiRepository.getMovies()

                _movieUiState.update {
                        currentState ->
                    currentState.copy(movies = listResult)
                }
                clearMovies()
                saveMovies()
            } catch (e: Exception) {
                Log.w("OFFLINE", "Service offline")
                val listResult = _movieStorageRepository.getAllMovies()
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
                    _movieApiRepository = movieApiRepository,
                    _movieStorageRepository = movieStorageRepository,
                    _authRepository = application.container.authRepository,
                    _movieLikeRepository = application.container.movieLikeRepository
                )
            }
        }
    }
}