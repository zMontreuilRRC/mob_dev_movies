package com.example.movies.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movies.model.Movie

@Composable
fun MovieCard (
    modifier: Modifier = Modifier,
    movieItem: Movie
)
{
    Column(
        modifier = Modifier
            .border(1.dp, Color.Red, shape = RectangleShape)
            .padding(5.dp)
    ) {
        Row (
            modifier = Modifier
                .background(color = Color.DarkGray)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            AsyncImage (
                model = "https://image.tmdb.org/t/p/w500${movieItem.posterPath}",
                contentDescription = "${movieItem.title} theatrical poster"
            )
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                movieItem.originalTitle?.let{
                    Text(
                        color = Color.White,
                        text = it,
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                        style = TextStyle(fontSize = 14.sp),
                        maxLines = 1
                    )
                }

                movieItem.overview?.let{
                    Text(
                        text = it,
                        modifier = Modifier.padding(end=8.dp),
                        maxLines = 9,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }

}