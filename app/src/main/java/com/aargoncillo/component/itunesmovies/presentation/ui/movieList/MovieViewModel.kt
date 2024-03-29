package com.aargoncillo.component.itunesmovies.presentation.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aargoncillo.component.itunesmovies.data.repository.MovieRepository
import com.aargoncillo.component.itunesmovies.domain.model.Movie
import com.aargoncillo.component.itunesmovies.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for [MovieFragment].
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel
@Inject internal constructor(
  private val movieRepository: MovieRepository
) : ViewModel() {

  val movieList = MutableStateFlow<Result<List<Movie>>?>(null)

  init {
    loadData(true)
  }

  fun loadData(isNetworkRequired: Boolean) {
    viewModelScope.launch {
      movieRepository.getListMovie(isNetworkRequired).collect {
        movieList.value = it
      }
    }
  }

  fun setFavorite(movie: Movie) {
    viewModelScope.launch {
      movieRepository.setFavorite(movie.trackId, !movie.isFavorite)
      loadData(false)
    }
  }
}