package com.example.movies

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.ui.theme.BottomNav
import com.example.movies.screens.trending.TrendingScreen
import com.example.movies.screens.common.MovieViewModel
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.screens.search.SearchScreen
import com.example.movies.screens.search.SearchViewModel
import com.example.movies.screens.signin.SignInScreen
import com.example.movies.screens.signin.SignInViewModel
import com.example.movies.screens.trending.TrendingViewModel
import com.example.movies.screens.watch.WatchScreen

sealed class Destination (val route: String) {
    object Movie: Destination("movies")
    object Watch: Destination("watch")
    object Search: Destination("search")
    object SignIn: Destination("signin")
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

        // store search values in viewmodel to keep values across navigations
        val trendingViewModel: TrendingViewModel = viewModel(factory = TrendingViewModel.Factory)
        val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
        val signinViewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory)

        signinViewModel.navigateOnSignIn = {
            signinViewModel.uiState.value = signinViewModel.uiState.value.copy(
                email = "",
                password = "",
                errorMessage = ""
            )
            navController.navigate(Destination.Movie.route)
        }

        NavHost(navController = navController, startDestination = Destination.SignIn.route) {
            composable(Destination.SignIn.route) {
                SignInScreen(signInViewModel = signinViewModel)
            }
            
            composable(Destination.Movie.route) {
                trendingViewModel.getMovieLikes()
                TrendingScreen(movieViewModel = trendingViewModel)
            }

            composable(Destination.Search.route) {
                searchViewModel.getMovieLikes()
                SearchScreen(searchViewModel = searchViewModel)
            }

            composable(Destination.Watch.route) {
                WatchScreen()
            }
        }
    }
}



