package com.example.movies.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.MovieApplication
import com.example.movies.data.AuthRepository
import com.example.movies.data.MovieApiRepository
import com.example.movies.data.MovieLikeRepository
import com.example.movies.model.Movie
import com.example.movies.screens.common.MovieVmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel(
    private val movieApiRepository: MovieApiRepository,
    private val authRepository: AuthRepository,
    private val movieLikeRepository: MovieLikeRepository
): ViewModel() {

    private val _searchUiState = MutableStateFlow(MovieVmUiState())
    val uiState: StateFlow<MovieVmUiState> = _searchUiState.asStateFlow()

    var searchValue by mutableStateOf("")
        private set

    fun handleSearchChange(input: String) {
        searchValue = input
    }

    fun searchForMovies() {
        val titleSearch = searchValue
        viewModelScope.launch {
            val dataResult = movieApiRepository.searchMovie(titleSearch)
            _searchUiState.update {
                currentState ->
                currentState.copy(movies = dataResult.results)
            }
        }

        searchValue = ""
    }


    // give access to app container
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieApiRepository
                SearchViewModel(
                    movieApiRepository = movieRepository,
                    authRepository = application.container.authRepository,
                    movieLikeRepository = application.container.movieLikeRepository
                )
            }
        }
    }
}