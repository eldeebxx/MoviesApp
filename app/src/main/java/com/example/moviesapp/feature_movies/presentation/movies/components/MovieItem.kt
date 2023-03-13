package com.example.moviesapp.feature_movies.presentation.movies.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.moviesapp.feature_movies.domain.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onclick: () -> Unit
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        val painter =
            rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
        val state = painter.state

        val transition by animateFloatAsState(
            targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .alpha(transition)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = movie.title,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movie.release_date)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Vote Average: ", fontWeight = FontWeight.Bold)
                Text(text = movie.vote_average.toString() + "/10")
            }

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedButton(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = onclick
            ) {
                Text(text = "Get Details")
            }
        }
    }

}