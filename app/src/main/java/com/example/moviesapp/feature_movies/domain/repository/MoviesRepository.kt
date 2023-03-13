package com.example.moviesapp.feature_movies.domain.repository

import com.example.moviesapp.core.util.Resource
import com.example.moviesapp.feature_movies.data.local.entity.MovieEntity
import com.example.moviesapp.feature_movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMoviesList(): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetails(id: Int): MovieEntity?
}