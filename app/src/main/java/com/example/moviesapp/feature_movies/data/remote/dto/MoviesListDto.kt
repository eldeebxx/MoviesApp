package com.example.moviesapp.feature_movies.data.remote.dto

import com.example.moviesapp.feature_movies.domain.model.MoviesList

data class MoviesListDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
) {
    fun toMoviesList(): MoviesList {
        return MoviesList(
            movies = results.map { it.toMovieEntity() }
        )
    }
}