package com.example.movies.screens.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MovieUiState (
    val movies: List<Movie> = mutableListOf(),
    val movieLikes: List<MovieLike> = mutableListOf()
)

open class MovieViewModel(
    private val _authRepository: AuthRepository,
    private val _movieLikeRepository: MovieLikeRepository
): ViewModel() {
    // private variable
    protected val movieUiState = MutableStateFlow(MovieVmUiState())
    val uiState: StateFlow<MovieVmUiState> = movieUiState.asStateFlow()

    // TODO: Add movie like functionality to main screen, extract into common movieVM ancestor
    fun getMovieLikes(userId: String) {
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
        try {
            val user: MovieUser = _authRepository.getCurrentUser()

            _movieLikeRepository.addMovieLike(user.id.toString(), movieId) {
                response ->
                Log.d("MOVIELIKE:POST", response.toString())
            }
        } catch(e: Exception) {
            Log.w("MOVIELIKE:POST", "Failed to post new Like data")
        }
    }


}