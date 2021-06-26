package com.aargoncillo.component.itunesmovies.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.aargoncillo.component.itunesmovies.R
import com.aargoncillo.component.itunesmovies.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

  private val movieViewModelMovie: MovieDetailViewModel by viewModels()

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
      viewModel = movieViewModelMovie
      lifecycleOwner = viewLifecycleOwner

      toolbar.setNavigationOnClickListener { view ->
        view.findNavController().navigateUp()
      }
    }

    setHasOptionsMenu(true)
    return binding.root
  }
}









