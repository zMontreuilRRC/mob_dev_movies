package com.example.movies.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.movies.ui.theme.MovieCard

@Composable
fun MovieCardDisplay (
    movieViewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    val movieUiState = movieViewModel.uiState.collectAsState()

    Column {

    LazyColumn{
        items(
            movieUiState.value.movies
        ) {
                movie ->
            MovieCard(
                movieItem = movie,
                isLiked = movieUiState.value.movieLikes.any() {
                    it.movieId == movie.id.toString()
                },
                onFavouriteClick = { movieViewModel.postMovieLikeToggle(movie.id.toString()) }
            )
        }
    }
}

}
