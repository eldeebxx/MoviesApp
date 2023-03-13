package com.example.moviesapp.feature_movies.data.repository

import com.example.moviesapp.core.util.Resource
import com.example.moviesapp.feature_movies.data.local.MoviesDao
import com.example.moviesapp.feature_movies.data.local.entity.MovieEntity
import com.example.moviesapp.feature_movies.data.remote.MoviesApi
import com.example.moviesapp.feature_movies.domain.model.Movie
import com.example.moviesapp.feature_movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val dao: MoviesDao
) : MoviesRepository {
    override fun getMoviesList(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        val movies = dao.getMoviesList().map { it.toMovie() }

        emit(Resource.Loading(data = movies))

        try {

            val remotePopularMoviesList = api.getPopularMoviesList()
            val remoteUpcomingMoviesList = api.getUpcomingMoviesList()

            dao.deleteMovies()

            dao.insertMovies(remotePopularMoviesList.toMoviesList().movies)
            dao.insertMovies(remoteUpcomingMoviesList.toMoviesList().movies)

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = movies
                )
            )
        } catch (e: IOException) {
            Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = movies
            )
        }

        val latestMoviesList = dao.getMoviesList().map { it.toMovie() }

        emit(Resource.Success(latestMoviesList))
    }

    override suspend fun getMovieDetails(id: Int): MovieEntity {
        return dao.getMovieDetails(id = id)
    }
}