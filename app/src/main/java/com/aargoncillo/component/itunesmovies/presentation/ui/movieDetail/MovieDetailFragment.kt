package com.aargoncillo.component.itunesmovies.presentation.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.aargoncillo.component.itunesmovies.R
import com.aargoncillo.component.itunesmovies.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

  private lateinit var binding: FragmentMovieDetailBinding
  private val movieDetailViewModel: MovieDetailViewModel by viewModels()


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    val binding = DataBindingUtil.inflate<FragmentMovieDetailBinding>(
      inflater,
      R.layout.fragment_movie_detail,
      container,
      false
    ).apply {
      // Apply data binding to [fragment_movie_detail] layout
      viewModel = movieDetailViewModel
      // Apply lifecycle owner to [fragment_movie_detail] layout
      lifecycleOwner = viewLifecycleOwner

      // Handle appbar back button action
      toolbar.setNavigationOnClickListener { view ->
        view.findNavController().navigateUp()
      }
    }
    setHasOptionsMenu(true)
    this.binding = binding
    setObserverEvent()
    setClickEvent()
    return binding.root
  }

  private fun setObserverEvent() {
    movieDetailViewModel.ageRating.onEach {
      binding.ageRatingText.text = it
    }.launchIn(lifecycleScope)
    movieDetailViewModel.releaseYear.onEach {
      binding.releaseYearText.text = it
    }.launchIn(lifecycleScope)
  }

  private fun setClickEvent() {
    binding.favoriteImage.setOnClickListener {
      movieDetailViewModel.setFavorite()
    }
  }
}









