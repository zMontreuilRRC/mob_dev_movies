package com.example.movies.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movies.ui.theme.MovieCard

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel
) {
    val searchUiState = searchViewModel.uiState.collectAsState()
    Column() {
        Row(
            modifier =  Modifier.padding(8.dp)) {
            TextField(
                value = searchViewModel.searchValue,
                onValueChange = { searchBarValue -> searchViewModel.handleSearchChange(searchBarValue)}
            )
            Button(
                onClick = { searchViewModel.searchForMovies() }
            ) {
                Text("Search")
            }
        }
        LazyColumn{
            items(searchUiState.value.movies) {
                    movie ->
                MovieCard(
                    movieItem = movie,
                    isLiked = searchUiState.value.movieLikes.any() {
                        it.movieId == movie.id.toString()
                    },
                    onFavouriteClick = {
                    // TODO: Add common functionality for movie like toggle between different VMs
                    }
                )
            }
        }
    }



}