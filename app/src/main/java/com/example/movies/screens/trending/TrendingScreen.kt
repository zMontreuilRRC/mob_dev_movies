package com.example.movies.screens.trending

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.movies.screens.common.MovieCardDisplay
import com.example.movies.screens.common.MovieViewModel
import com.example.movies.ui.theme.MovieCard

@Composable
fun TrendingScreen(
    modifier: Modifier = Modifier,
    movieViewModel: TrendingViewModel
) {
    MovieCardDisplay(movieViewModel = movieViewModel)
}