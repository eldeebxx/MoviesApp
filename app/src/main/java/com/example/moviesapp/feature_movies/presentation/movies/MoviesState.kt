package com.example.moviesapp.feature_movies.presentation.movies

import com.example.moviesapp.feature_movies.domain.model.Movie

data class MoviesState(
    val moviesList: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)
