package com.example.movies.screens.common

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.movies.data.AuthRepository
import com.example.movies.data.MovieLikeRepository
import com.example.movies.model.Movie
import com.example.movies.model.MovieLike
import com.example.movies.model.MovieUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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


    fun getMovieLikes() {
        try {
                val user:MovieUser = _authRepository.getCurrentUser()
                _movieLikeRepository.getMovieLikes(user.id) {
                    newMovieLikes ->
                    movieUiState.update {
                        currentState ->
                        currentState.copy(
                            movieLikes = newMovieLikes
                        )
                    }
                }
        } catch(e: Exception) {
            Log.w("GET:MOVIELIKES", e.toString())
        }
    }

    fun postMovieLikeToggle(movieId: String) {
        try {
            val user: MovieUser = _authRepository.getCurrentUser()

            if(movieUiState.value.movieLikes.any{
                ml -> ml.movieId == movieId && ml.userId == user.id
            }) {
                _movieLikeRepository.removeMovieLike(user.id, movieId) {
                    getMovieLikes()
                }
            } else {
                _movieLikeRepository.addMovieLike(user.id.toString(), movieId) {
                    getMovieLikes()
                }
            }

        } catch(e: Exception) {
            Log.w("MOVIELIKE:POST", "Failed to post new Like data")
        }
    }
}