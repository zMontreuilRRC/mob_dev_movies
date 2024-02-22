package com.example.movies.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.movies.model.Movie

@Composable
fun MovieScreen(movieViewModel: MovieViewModel,
                modifier: Modifier = Modifier) {
    val moviesUiState = movieViewModel.uiState.collectAsState()

    Column () {
        moviesUiState.value.movies.forEach() { m ->
            Text(m.title)
        }
    }

}