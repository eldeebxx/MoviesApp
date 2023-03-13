package com.example.moviesapp.feature_movies.presentation.movie_details

import com.example.moviesapp.feature_movies.domain.model.Movie

data class MovieDetailsState(
    val movieDetails: Movie = Movie(
        id = 0,
        title = "",
        vote_average = 0.0,
        release_date = "",
        poster_path = "",
        vote_count = 0,
        overview = ""
    ),
    val isLoading: Boolean = false
)
