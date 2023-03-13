package com.example.moviesapp.feature_movies.presentation.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesapp.feature_movies.presentation.movies.components.HeadingTitle
import com.example.moviesapp.feature_movies.presentation.movies.components.MoviesList
import com.example.moviesapp.feature_movies.presentation.util.Screen
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(navController: NavController, viewModel: MoviesViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Latest Movies")
            })
        },
    ) { paddingValues ->
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                HeadingTitle(
                    text = "Most Popular",
                    isLoading = state.isLoading,
                )
                MoviesList(
                    modifier = Modifier.width(screenWidth - screenWidth / 6),
                    onClick = { id ->
                        navController.navigate(
                            Screen.MovieDetails.route + "?movieId=${id}"
                        )
                    },
                    list = state.moviesList.sortedByDescending { it.vote_average }.filter {
                        it.release_date < LocalDate.now().toString()
                    }
                )

                HeadingTitle(
                    text = "Upcoming",
                    isLoading = state.isLoading,
                )

                MoviesList(
                    modifier = Modifier.width(screenWidth - screenWidth / 6),
                    onClick = { id ->
                        navController.navigate(
                            Screen.MovieDetails.route + "?movieId=${id}"
                        )
                    },
                    list = state.moviesList.filter {
                        it.release_date > LocalDate.now().toString()
                    }
                )
            }
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}