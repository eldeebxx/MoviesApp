package com.example.moviesapp.feature_movies.presentation.movie_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.feature_movies.domain.use_case.MovieUseCase
import com.example.moviesapp.feature_movies.presentation.movies.MoviesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailsState())
    val state: State<MovieDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<MoviesViewModel.UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentMovieId: Int? = null

    init {
        savedStateHandle.get<Int>("movieId")?.let { movieId ->
            if (movieId != -1) {
                getMovieDetails(movieId = movieId)
            }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            delay(500)
            movieUseCase.getMovieDetails(movieId).also { movie ->
                currentMovieId = movie?.id ?: -1
                _state.value = _state.value.copy(
                    movieDetails = movie?.toMovie()!!,
                    isLoading = false
                )
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}