package com.aargoncillo.component.itunesmovies.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aargoncillo.component.itunesmovies.databinding.ItemMovieBinding
import com.aargoncillo.component.itunesmovies.domain.model.Movie

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    MovieViewHolder(
      ItemMovieBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = getItem(position)
    holder.bind(movie)
  }

  inner class MovieViewHolder(
    private val binding: ItemMovieBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.setClickListener { view ->
        binding.movie?.let { movie ->
          navigateToDetailFragment(movie, view)
        }
      }
    }

    private fun navigateToDetailFragment(
      movie: Movie,
      view: View
    ) {
      val direction = MovieFragmentDirections.actionMovieListToMovieDetail(movie.trackId)
      view.findNavController().navigate(direction)
    }

    fun bind(item: Movie) {
      binding.apply {
        movie = item
        executePendingBindings()
      }
    }
  }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
  override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
    oldItem.trackId == newItem.trackId

  override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
    oldItem == newItem
}
