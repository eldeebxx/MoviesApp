package com.example.moviesapp.feature_movies.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapp.feature_movies.data.local.MoviesDatabase
import com.example.moviesapp.feature_movies.data.remote.MoviesApi
import com.example.moviesapp.feature_movies.data.repository.MoviesRepositoryImpl
import com.example.moviesapp.feature_movies.domain.repository.MoviesRepository
import com.example.moviesapp.feature_movies.domain.use_case.GetMovieDetails
import com.example.moviesapp.feature_movies.domain.use_case.GetMoviesList
import com.example.moviesapp.feature_movies.domain.use_case.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: MoviesRepository): MovieUseCase {
        return MovieUseCase(
            getMoviesList = GetMoviesList(repository),
            getMovieDetails = GetMovieDetails(repository),
        )
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(db: MoviesDatabase, api: MoviesApi): MoviesRepository {
        return MoviesRepositoryImpl(api = api, dao = db.dao)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDatabase {
        return Room.databaseBuilder(
            app,
            MoviesDatabase::class.java,
            name = "movies_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }
}