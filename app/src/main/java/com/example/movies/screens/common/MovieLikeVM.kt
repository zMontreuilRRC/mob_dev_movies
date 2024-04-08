package com.example.movies.screens.common

import androidx.lifecycle.ViewModel
import com.example.movies.model.Movie
import com.example.movies.model.MovieLike

// provide like handling functionality to all inheriting VMs
data class MovieVmUiState(
    val movies: List<Movie> = mutableListOf(),
    val movieLikes: List<MovieLike> = mutableListOf()
)