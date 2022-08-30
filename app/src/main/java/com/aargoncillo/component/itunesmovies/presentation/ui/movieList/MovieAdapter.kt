package com.aargoncillo.component.itunesmovies.presentation.ui.movieList

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

    /**
     * handles click event where if item is clicked, navigate to [MovieDetailsFragment] fragment
     * and attaches selected item in list as an argument
     */
    init {
      binding.setClickListener { view ->
        binding.movie?.let { movie ->
          navigateToDetailFragment(movie, view)
        }
      }
    }

    /**
     * handles navigation to [MovieDetailFragment] fragment
     * attaches argument [Movie] to be used by the next fragment
     *
     * @param movie data to be attached to arguments so next fragment can collect the data
     * @param view reference for fragment navigation
     */
    private fun navigateToDetailFragment(
      movie: Movie,
      view: View
    ) {
      val direction = MovieFragmentDirections.actionMovieListToMovieDetail(movie.trackId)
      view.findNavController().navigate(direction)
    }

    fun bind(item: Movie) {
      /**
       * Apply data binding to [item_movie] layout.
       */
      binding.apply {
        movie = item
        executePendingBindings()
      }
    }
  }
}

/**
 * Class for comparing and replacing data which is to be updated by the adapter
 */
private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
  override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
    oldItem.trackId == newItem.trackId

  override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
    oldItem == newItem
}
