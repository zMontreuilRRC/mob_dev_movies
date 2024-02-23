package com.example.movies.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movies.R
import com.example.movies.model.Movie

@Composable
fun MovieScreen(movieUiState: MovieUiState,
                modifier: Modifier = Modifier) {

    Column () {
        when(movieUiState) {
            is MovieUiState.Loading -> Text("Loading!")
            is MovieUiState.Error -> Text(stringResource(R.string.error_no_movies))
            is MovieUiState.Success -> {
                LazyColumn {
                    items(movieUiState.movies) {
                        movie ->
                        MovieCard(movieItem = movie)
                    }
                }
            }
        }
    }

}