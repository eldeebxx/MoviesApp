package com.example.moviesapp.feature_movies.presentation.movies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesapp.feature_movies.domain.model.Movie
import com.example.moviesapp.feature_movies.presentation.util.Screen

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    list: List<Movie>
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { movie ->
            MovieItem(
                movie = movie,
                modifier = modifier,
                onclick = {
                    onClick(movie.id)
                }
            )
        }
    }
}