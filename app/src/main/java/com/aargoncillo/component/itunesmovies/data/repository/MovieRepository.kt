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

  suspend fun getListMovie(): Flow<Result<List<Movie>>?> {
    return flow {
      // Emits the cached values from local database
      emit(fetchTrendingMoviesCached())
      // Emits the loading state
      emit(Result.loading())
      val result = api.fetchMovieList()
      // Cache to database if response is successful
      if (result.status == Result.Status.SUCCESS) {
        result.data?.results?.let {
          mapper.toDomainList(it)
          database.deleteAll(mapper.toDomainList(it))
          database.insertAll(mapper.toDomainList(it))
          // Emits the result from the API response
          emit(Result.success(mapper.toDomainList(it)))
        }
      } else {
        // Emmit error result
        emit(Result.error<List<Movie>>(result.message, result.error))
      }
    }.flowOn(Dispatchers.IO) // Run on background thread
  }

  fun getMovie(trackId: Int) = database.getMovie(trackId)

  private fun fetchTrendingMoviesCached(): Result<List<Movie>>? =
    database.getMovieList()?.let { Result.success(it) }
}
