package com.example.moviesapp.feature_movies.data.remote.dto

import com.example.moviesapp.feature_movies.data.local.entity.MovieEntity

data class MovieDto(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            overview = overview,
            poster_path = poster_path,
            release_date = release_date,
            title = title,
            vote_average = vote_average,
            vote_count = vote_count
        )
    }
}