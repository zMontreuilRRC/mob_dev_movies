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

data class MovieUiState (
    val movies: List<Movie> = mutableListOf()
)

class MovieViewModel: ViewModel() {
    // private variable
    private val _movieUiState = MutableStateFlow(MovieUiState())
    
    val uiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    init{
        getTrendingMovies()
    }
    private fun getTrendingMovies() {
        viewModelScope.launch {
            val listResult = MovieApi.retrofitService.getMovies()
            _movieUiState.update {
                currentState ->
                currentState.copy(movies = listResult.results)
            }
        }
    }
}