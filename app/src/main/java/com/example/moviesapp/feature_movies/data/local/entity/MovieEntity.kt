package com.example.moviesapp.feature_movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapp.feature_movies.domain.model.Movie

@Entity(tableName = "MoviesTable")
data class MovieEntity(
    @PrimaryKey val id: Int? = null,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toMovie(): Movie {
        return Movie(
            id = id ?: 0,
            overview = overview,
            poster_path = poster_path,
            release_date = release_date,
            title = title,
            vote_average = vote_average,
            vote_count = vote_count
        )
    }
}
