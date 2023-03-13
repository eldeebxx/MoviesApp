package com.example.moviesapp.feature_movies.data.remote

import com.example.moviesapp.feature_movies.data.remote.dto.MoviesListDto
import retrofit2.http.GET

interface MoviesApi {

    @GET("movie/popular?api_key=$API_KEY&language=$LANG")
    suspend fun getPopularMoviesList(): MoviesListDto

    @GET("movie/upcoming?api_key=$API_KEY&language=$LANG")
    suspend fun getUpcomingMoviesList(): MoviesListDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "XXXXXXX"
        const val LANG = "en-US"
    }
}
