package com.example.moviesapp.feature_movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.moviesapp.feature_movies.presentation.movie_details.MovieDetailsScreen
import com.example.moviesapp.feature_movies.presentation.movies.MoviesScreen
import com.example.moviesapp.feature_movies.presentation.movies.MoviesViewModel
import com.example.moviesapp.feature_movies.presentation.util.Screen
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {

                val viewModel: MoviesViewModel by viewModels()
                val snackBarHostState = remember {
                    SnackbarHostState()
                }
                val navController = rememberAnimatedNavController()

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is MoviesViewModel.UIEvent.ShowSnackBar -> {
                                snackBarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screen.MoviesScreen.route
                ) {
                    composable(Screen.MoviesScreen.route) {
                        MoviesScreen(navController = navController)
                    }

                    composable(Screen.MovieDetails.route + "?movieId={movieId}",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentScope.SlideDirection.Right,
                                tween(700)
                            )
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                Screen.MoviesScreen.route ->
                                    slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Left,
                                        animationSpec = tween(700)
                                    )
                                else -> null
                            }
                        },
                        arguments = listOf(
                            navArgument("movieId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                        MovieDetailsScreen(navController = navController)
                    }
                }
            }
        }
    }
}
