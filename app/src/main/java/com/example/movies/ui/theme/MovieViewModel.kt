package com.example.movies.ui.theme

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.MovieRepository
import com.example.movies.data.NetworkMovieRepository
import com.example.movies.model.Movie
import com.example.movies.model.MovieData
import com.example.movies.network.MovieApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieUiState (
    val movies: List<Movie> = mutableListOf()
)

class MovieViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {
    // private variable
    private val _movieUiState = MutableStateFlow(MovieUiState())
    
    val uiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    init{
        getTrendingMovies()
    }
    private fun getTrendingMovies() {
        viewModelScope.launch {
            val listResult = movieRepository.getMovieData()
            _movieUiState.update {
                currentState ->
                currentState.copy(movies = listResult.results)
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
                val movieRepository = application.container.movieRepository
                MovieViewModel(movieRepository = movieRepository)
            }
        }
    }
}