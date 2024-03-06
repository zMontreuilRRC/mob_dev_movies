package com.example.movies.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.MovieApiRepository
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieUiState (
    val movies: List<Movie> = mutableListOf()
)

class MovieViewModel(
    private val movieApiRepository: MovieApiRepository
): ViewModel() {
    // private variable
    private val _movieUiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    init{
        getTrendingMovies()
    }


    private fun getTrendingMovies() {
        viewModelScope.launch {
            val listResult = movieApiRepository.getMovieData()
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
                val movieRepository = application.container.movieApiRepository
                MovieViewModel(movieApiRepository = movieRepository)
            }
        }
    }
}