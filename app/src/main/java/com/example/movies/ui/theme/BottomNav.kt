package com.example.movies.ui.theme

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movies.Destination
import com.example.movies.R

@Composable
fun BottomNav(navController: NavController, modifier: Modifier = Modifier) {
    BottomNavigation (elevation = 7.dp) {
        val navBackStackEntry = navController.currentBackStackEntry
        val currentDestination = navBackStackEntry?.destination

        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Movie.route,
            onClick = {
                navController.navigate(Destination.Movie.route) {
                    popUpTo(Destination.Movie.route)
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.film),
                    contentDescription = "Films Screen",
                    modifier = Modifier.size(26.dp)
                )
            },
            label = { Text(text = Destination.Movie.route.replaceFirstChar {it.uppercase()})}
        )


        BottomNavigationItem(
            selected = currentDestination?.route == Destination.Watch.route,
            onClick = {
                navController.navigate(Destination.Watch.route) {
                    popUpTo(Destination.Watch.route)
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "Watch Screen",
                    modifier = Modifier.size(26.dp)
                )
            },
            label = { Text(text = Destination.Watch.route.replaceFirstChar {it.uppercase() })}
        )

    }
}