package com.aargoncillo.component.itunesmovies.presentation.ui.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aargoncillo.component.itunesmovies.databinding.ItemMovieBinding
import com.aargoncillo.component.itunesmovies.domain.model.Movie
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter(val handler: Callbacks) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()), Filterable {

  var movieList: ArrayList<Movie>? = ArrayList()
  private var movieListFiltered: ArrayList<Movie>? = ArrayList()

  inner class MovieViewHolder(
    private val binding: ItemMovieBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      // Navigation to [MovieDetailsFragment]
      binding.setClickListener { view ->
        binding.movie?.let { movie ->
          navigateToDetailFragment(movie, view)
        }
      }
      // Set movie as favorite
      binding.favoriteImage.setOnClickListener {
        binding.movie?.let { movie ->
          handler.setFavorite(movie)
        }
      }
    }

    /**
     * handles navigation to [MovieDetailFragment] fragment
     * attaches model [Movie] to be used by the next fragment
     *
     * @param movie data to be attached to the arguments so next fragment can collect the data
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

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun performFiltering(charSequence: CharSequence?): FilterResults {
        val charString = charSequence?.toString()?.lowercase() ?: ""
        if (charString.isEmpty()) {
          movieListFiltered = movieList
        } else {
          movieList?.let { list ->
            val filteredList = arrayListOf<Movie>()
            list.filter {
              (it.trackName?.lowercase()?.contains(charString) == true).
              or((it.genre?.lowercase()?.contains(charString) == true)).
              or((it.releaseDate?.lowercase()?.contains(charString)== true))
            }.forEach {
              filteredList.add(it)
            }
            movieListFiltered = filteredList
          }
        }
        val filterResults = FilterResults()
        filterResults.values = movieListFiltered
        return filterResults
      }

      override fun publishResults(
        charSequence: CharSequence,
        filterResults: FilterResults
      ) {
        movieListFiltered = filterResults.values as ArrayList<Movie>
        handler.isSearchEmpty(movieListFiltered.isNullOrEmpty())
        submitList(movieListFiltered)
      }
    }
  }

  interface Callbacks {
    fun setFavorite(movie: Movie)
    fun isSearchEmpty(isEmpty: Boolean)
  }
}

/**
 * Class for comparing and replacing data which is to be updated by the adapter
 */
private class MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {
  override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
    oldItem.trackId == newItem.trackId

  override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
    oldItem == newItem
}


