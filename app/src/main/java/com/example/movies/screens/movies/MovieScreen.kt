package com.example.movies.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.movies.ui.theme.MovieCard

@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel
) {
    val moviesUiState = movieViewModel.uiState.collectAsState()

    Column () {

        LazyColumn{
            items(moviesUiState.value.movies) {
                movie ->
                MovieCard(
                    movieItem = movie,
                    isLiked = moviesUiState.value.movieLikes.any() {
                        it.movieId == movie.id.toString()
                    },
                    onFavouriteClick = { movieViewModel.postMovieLike(movie.id.toString()) }
                )
            }
        }
    }

}