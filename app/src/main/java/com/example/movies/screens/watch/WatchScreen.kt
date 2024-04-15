package com.example.movies.screens.watch

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movies.screens.common.MovieCardDisplay

@Composable
fun WatchScreen(modifier: Modifier = Modifier, watchViewModel: WatchViewModel) {
    MovieCardDisplay(movieViewModel =  watchViewModel)
}