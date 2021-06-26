package com.aargoncillo.component.itunesmovies.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject internal constructor(
//  private val movieRepository: MovieRepository,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val movieId = savedStateHandle.get<Int>(MOVIE_ID_SAVED_STATE_KEY)!!
//  val movie = movieRepository.getMovie(movieId).asLiveData()


//  var listPosition: Int = 0

  // Save current scroll position and save state
//  fun onChangeScrollPosition(position: Int){
//    listPosition = position
//    savedStateHandle.set(STATE_LIST_POSITION, position)
//  }

  companion object {
    // Key from [graph_list] arg
    private const val MOVIE_ID_SAVED_STATE_KEY = "movieId"

    // Save current app state
//    private const val STATE_LIST_POSITION = "movie.state.list_position"
//    private const val STATE_SELECTED_ITEM_LIST = "movie.state.selected_item_list"
  }
}



























