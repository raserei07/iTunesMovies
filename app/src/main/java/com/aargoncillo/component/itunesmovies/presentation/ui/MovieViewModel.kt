package com.aargoncillo.component.itunesmovies.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aargoncillo.component.itunesmovies.data.repository.MovieRepository
import com.aargoncillo.component.itunesmovies.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for [MovieFragment].
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel
@Inject internal constructor(
  private val movieRepository: MovieRepository,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  val movieList = MutableStateFlow<Result<List<Movie>>?>(null)

  init {
    loadData()
  }

  fun loadData() {
    viewModelScope.launch { movieRepository.getListMovie().collect { movieList.value = it } }
  }
}