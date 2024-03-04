package com.example.movies.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.MovieRepository
import com.example.movies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SearchUiState (
    val movies: List<Movie> = mutableListOf()
)

class SearchViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    var searchValue by mutableStateOf("")
        private set

    fun handleSearchChange(input: String) {
        searchValue = input
    }

    fun searchForMovies() {

    }


    // give access to app container
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                SearchViewModel(movieRepository = movieRepository)
            }
        }
    }
}