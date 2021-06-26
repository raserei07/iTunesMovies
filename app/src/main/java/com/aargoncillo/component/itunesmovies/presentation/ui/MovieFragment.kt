package com.aargoncillo.component.itunesmovies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aargoncillo.component.itunesmovies.databinding.FragmentMovieListBinding
import com.google.android.material.snackbar.Snackbar
import com.aargoncillo.component.itunesmovies.domain.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieFragment : Fragment() {

  private val viewModel: MovieViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    val binding = FragmentMovieListBinding.inflate(inflater, container, false)
    context ?: return binding.root
    val adapter = MovieAdapter()

    // Set adapter
    binding.movieList.hasFixedSize()
    binding.movieList.adapter = adapter

    // Set swipe refresh
    binding.swipeRefresh.setOnRefreshListener { viewModel.loadData() }

    // Set observables
    subscribeUi(adapter, binding)

    return binding.root
  }

  override fun onResume() {
    super.onResume()
    viewModel.loadData()
  }

  private fun subscribeUi(
    adapter: MovieAdapter,
    binding: FragmentMovieListBinding
  ) {
    viewModel.movieList.onEach {
      it?.also { result ->
        when (result.status) {
          Result.Status.SUCCESS -> {
            result.data?.let { list ->
              // Update list in adapter
              adapter.submitList(list)
            }
            //loading.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
          }
          Result.Status.ERROR -> {
            result.message?.let { error ->
              showError(error)
            }
            //loading.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
          }
          Result.Status.LOADING -> {
            binding.swipeRefresh.isRefreshing = true
          }
        }
      }
    }.launchIn(lifecycleScope)
  }

  private fun showError(msg: String) {
    view?.let {
      Snackbar.make(it, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
      }.show()
    }
  }
}