package com.example.moviesapp.feature_movies.domain.use_case

import com.example.moviesapp.feature_movies.data.local.entity.MovieEntity
import com.example.moviesapp.feature_movies.domain.repository.MoviesRepository

class GetMovieDetails(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): MovieEntity? {
        return repository.getMovieDetails(id = id)
    }
}