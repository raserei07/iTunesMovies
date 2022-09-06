package com.aargoncillo.component.itunesmovies.presentation.ui.movieList

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aargoncillo.component.itunesmovies.R
import com.aargoncillo.component.itunesmovies.databinding.FragmentMovieListBinding
import com.aargoncillo.component.itunesmovies.domain.model.Movie
import com.aargoncillo.component.itunesmovies.domain.model.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MovieFragment : Fragment() {

  private lateinit var binding: FragmentMovieListBinding
  private val viewModel: MovieViewModel by viewModels()
  private val adapter = MovieAdapter(adapterCallback())

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieListBinding.inflate(inflater, container, false)
    setToolbar()
    setAdapter()
    setObserverEvent()
    return binding.root
  }

  override fun onResume() {
    super.onResume()
    viewModel.loadData(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.search_menu, menu)
    setSearchView(menu)
  }

  private fun setSearchView(menu: Menu) {
    val search = menu.findItem(R.id.menu_search)
    val searchView: SearchView = search.actionView as SearchView
    val params: ActionBar.LayoutParams = ActionBar.LayoutParams(
      ActionBar.LayoutParams.MATCH_PARENT,
      ActionBar.LayoutParams.MATCH_PARENT
    )
    searchView.apply {
      isSubmitButtonEnabled = true
      setOnQueryTextListener(searchCallback())
      layoutParams = params
    }
    val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
    searchEditText.setTextColor(resources.getColor(R.color.white))
    searchEditText.setHintTextColor(resources.getColor(R.color.white))
    val searchImage = searchView.findViewById<View>(androidx.appcompat.R.id.search_button) as ImageView
    searchImage.setImageResource(R.drawable.ic_search)
    val searchCloseImage = searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn) as ImageView
    searchCloseImage.setImageResource(R.drawable.ic_close)
    val searchGoImage = searchView.findViewById<View>(androidx.appcompat.R.id.search_go_btn) as ImageView
    searchGoImage.setImageResource(R.drawable.ic_chevron_right)
  }

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).setSupportActionBar(binding.movieToolbar)
    setHasOptionsMenu(true)
  }

  private fun setAdapter() {
    binding.movieList.hasFixedSize()
    binding.movieList.adapter = adapter
    binding.movieSwipeRefresh.setOnRefreshListener { viewModel.loadData(true) }
  }

  private fun setObserverEvent() {
    viewModel.movieList.onEach {
      it?.also { result ->
        when (result.status) {
          Result.Status.SUCCESS -> {
            result.data?.let { list ->
              if (adapter.movieList.isNullOrEmpty()) {
                adapter.movieList = list as ArrayList<Movie>
              }
              adapter.submitList(list)
            }
            binding.movieSwipeRefresh.isRefreshing = false
          }
          Result.Status.ERROR -> {
            result.message?.let { error ->
              showError(error)
            }
            binding.movieSwipeRefresh.isRefreshing = false
          }
          Result.Status.LOADING -> {
            binding.movieSwipeRefresh.isRefreshing = true
          }
        }
      }
    }.launchIn(lifecycleScope)
  }

  private fun showError(msg: String) {
    view?.let {
      Snackbar.make(it, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {}.show()
    }
  }

  private fun adapterCallback(): MovieAdapter.Callbacks = object : MovieAdapter.Callbacks {
    override fun setFavorite(movie: Movie) {
      viewModel.setFavorite(movie)
    }
    override fun isSearchEmpty(isEmpty: Boolean) {
      binding.emptyText.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
  }

  private fun searchCallback(): SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean = false
    override fun onQueryTextChange(newText: String?): Boolean {
      (binding.movieList.adapter as MovieAdapter).filter.filter(newText)
      return true
    }
  }
}