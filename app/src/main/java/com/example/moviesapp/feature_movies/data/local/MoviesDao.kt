package com.example.moviesapp.feature_movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.feature_movies.data.local.entity.MovieEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM MoviesTable")
    suspend fun deleteMovies()

    @Query("SELECT * FROM MoviesTable WHERE id=:id")
    suspend fun getMovieDetails(id: Int): MovieEntity

    @Query("SELECT * FROM MoviesTable")
    suspend fun getMoviesList(): List<MovieEntity>

}