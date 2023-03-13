package com.example.moviesapp.feature_movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.feature_movies.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1, exportSchema = false
)

abstract class MoviesDatabase : RoomDatabase() {
    abstract val dao: MoviesDao
}