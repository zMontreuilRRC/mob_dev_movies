package com.example.movies.ui.theme

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.Movie
import com.example.movies.model.MovieData
import com.example.movies.network.MovieApi
import com.example.movies.network.MovieApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface MovieUiState {
    data class Success(val movies: List<Movie>): MovieUiState
    object Error: MovieUiState
    object Loading: MovieUiState
}


class MovieViewModel: ViewModel() {
    // private variable
    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    init{
        getTrendingMovies()
    }
    private fun getTrendingMovies() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading

            val listResult = MovieApi.retrofitService.getMovies()

            movieUiState = try {
                val movies = MovieApi.retrofitService.getMovies()
                MovieUiState.Success(
                    movies = movies.results
                )
            } catch (e: Exception) {
                MovieUiState.Error
            }
        }
    }
}