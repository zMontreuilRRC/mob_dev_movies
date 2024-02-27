package com.example.movies

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.ui.theme.BottomNav
import com.example.movies.ui.theme.MovieScreen
import com.example.movies.ui.theme.MovieViewModel
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.ui.theme.WatchScreen

sealed class Destination (val route: String) {
    object Movie: Destination("movies")
    object Watch: Destination("watch")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // controller
                    val navController = rememberNavController()
                    MovieScaffold(navController)
                }
            }
        }
    }
}

// controller: coordinates navigation throughout the app
// navhost: ui element, swaps out destinations
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MovieScaffold(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { 
            BottomNav(navController = navController)
        }
    ) {
        paddingValues ->
        val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory)

        NavHost(navController = navController, startDestination = Destination.Movie.route) {
            composable(Destination.Movie.route) {
                MovieScreen(movieViewModel)
            }

            composable(Destination.Watch.route) {
                WatchScreen()
            }
        }
    }
}



