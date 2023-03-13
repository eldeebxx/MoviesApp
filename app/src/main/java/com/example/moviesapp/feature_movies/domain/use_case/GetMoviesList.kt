package com.example.moviesapp.feature_movies.domain.use_case

import com.example.moviesapp.core.util.Resource
import com.example.moviesapp.feature_movies.domain.model.Movie
import com.example.moviesapp.feature_movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesList(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<Resource<List<Movie>>> {
        return repository.getMoviesList()
    }
}