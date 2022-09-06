package com.aargoncillo.component.itunesmovies.presentation.ui.movieDetail

import androidx.lifecycle.*
import com.aargoncillo.component.itunesmovies.data.repository.MovieRepository
import com.aargoncillo.component.itunesmovies.domain.model.Movie
import com.aargoncillo.component.itunesmovies.util.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for [MovieDetailFragment].
 */
@HiltViewModel
class MovieDetailViewModel @Inject internal constructor(
  private val movieRepository: MovieRepository,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val movieId = savedStateHandle.get<Int>(MOVIE_ID_SAVED_STATE_KEY)!!
  // Movie Details
  val movie = movieRepository.getMovie(movieId).asLiveData()
  // Rating Advisory
  private val _ageRating = MutableSharedFlow<String?>()
  val ageRating = _ageRating.asSharedFlow()
  // Year of release
  private val _releaseYear = MutableSharedFlow<String?>()
  val releaseYear = _releaseYear.asSharedFlow()

  init {
    movieRepository.getMovie(movieId).onEach {
      setRatingAdvisory(it)
      setYearReleased(it)
    }.launchIn(viewModelScope)
  }

  private suspend fun setRatingAdvisory(movie: Movie?) {
    movie?.ratingAdvisory.apply {
      var rating = ""
      when (this) {
        "M" -> rating = "16+"
        "PG" -> rating = "8+"
        "MA15+" -> rating = "18+"
        "G" -> rating = "5+"
      }
      _ageRating.emit(rating)
    }
  }

  private suspend fun setYearReleased(movie: Movie?) {
    movie?.releaseDate.apply {
      _releaseYear.emit(DateTimeUtils().getYear(this))
    }
  }

  fun setFavorite() {
    viewModelScope.launch {
      movie.value?.isFavorite?.apply {
        movieRepository.setFavorite(movieId, !this)
      }
    }
  }

  companion object {
    // Key from [graph_list] arg
    private const val MOVIE_ID_SAVED_STATE_KEY = "MOVIE_ID_SAVED_STATE_KEY"
  }
}



























