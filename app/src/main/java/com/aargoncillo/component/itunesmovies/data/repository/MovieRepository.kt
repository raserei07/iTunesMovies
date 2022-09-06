package com.aargoncillo.component.itunesmovies.data.repository

import com.aargoncillo.component.itunesmovies.data.local.dao.MovieDao
import com.aargoncillo.component.itunesmovies.data.network.dataSource.MovieNetworkDataSource
import com.aargoncillo.component.itunesmovies.data.network.mapper.MovieDtoMapper
import com.aargoncillo.component.itunesmovies.domain.model.Movie
import com.aargoncillo.component.itunesmovies.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [MovieDao] is main-safe.
 */
class MovieRepository @Inject constructor(
  private val mapper: MovieDtoMapper,
  private val database: MovieDao,
  private val api: MovieNetworkDataSource
) {

  suspend fun getListMovie(isNetworkRequired: Boolean): Flow<Result<List<Movie>>?> {
    return flow {
      // Emits the cached values from local database
      emit(fetchTrendingMoviesCached())
      if (!isNetworkRequired) return@flow
      // Emits the loading state
      emit(Result.loading())
      val result = api.fetchMovieList()
      // Cache to database if response is successful
      if (result.status == Result.Status.SUCCESS) {
        result.data?.results?.let {
          // checks if database is empty, insert or update the list
          database.insertAll(mapper.toDomainList(it))
          // Emits the result from the API response
          emit(Result.success(database.getMovieList()))
        }
      } else {
        // Emmit error result
        emit(Result.error<List<Movie>>(result.message, result.error))
      }
    }.flowOn(Dispatchers.IO) // Run on background thread
  }

  fun getMovie(trackId: Int) = database.getMovie(trackId)

  suspend fun setFavorite(trackId: Int, isFavorite: Boolean) {
    withContext(Dispatchers.IO) {
      database.setFavorite(trackId, isFavorite)
    }
  }

  private fun fetchTrendingMoviesCached(): Result<List<Movie>>? = database.getMovieList()?.let { Result.success(it) }
}
