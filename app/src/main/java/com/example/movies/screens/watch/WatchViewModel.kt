package com.example.movies.screens.watch

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.AuthRepository
import com.example.movies.data.MovieApiRepository
import com.example.movies.data.MovieLikeRepository
import com.example.movies.model.Movie
import com.example.movies.model.MovieUser
import com.example.movies.screens.common.MovieViewModel
import com.example.movies.screens.trending.TrendingViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WatchViewModel(
    private val _authRepository: AuthRepository,
    private val _movieLikeRepository: MovieLikeRepository,
    private val _movieApiRepository: MovieApiRepository,
    ) : MovieViewModel(_authRepository, _movieLikeRepository)
{
    fun getLikedMovies() {
        val user: MovieUser = _authRepository.getCurrentUser()
        _movieLikeRepository.getMovieLikes(user.id) {
                likes ->
            viewModelScope.launch {
                _movieApiRepository.getLikedMovies(user.id, likes) {
                    result ->
                    movieUiState.update {
                        currentState ->
                        currentState.copy(movies = result)
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                WatchViewModel(
                    _movieApiRepository = application.container.movieApiRepository,
                    _authRepository = application.container.authRepository,
                    _movieLikeRepository = application.container.movieLikeRepository
                )
            }
        }
    }
}