package com.example.moviesapp.feature_movies.presentation.util

sealed class Screen(val route: String) {
    object MoviesScreen : Screen("movies_screen")
    object MovieDetails : Screen("movie_details")
}
