package com.aargoncillo.component.itunesmovies.data.network.dataSource

import com.aargoncillo.component.itunesmovies.data.network.MovieApi
import com.aargoncillo.component.itunesmovies.data.network.model.MovieDto
import com.aargoncillo.component.itunesmovies.data.network.model.ResponseDto
import com.aargoncillo.component.itunesmovies.domain.model.Result
import com.aargoncillo.component.itunesmovies.util.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MovieNetworkDataSource
@Inject constructor(
  private val retrofit: Retrofit
) {

  suspend fun fetchMovieList(): Result<ResponseDto<MovieDto>> {
    val service = retrofit.create(MovieApi::class.java)
    return getResponse(
      request = { service.getAllMovies() },
      defaultErrorMessage = "Error fetching Movie list"
    )
  }

  /**
   * Exception handler when handling service return
   */
  private suspend fun <T> getResponse(
    request: suspend () -> Response<T>,
    defaultErrorMessage: String
  ): Result<T> {
    return try {
      val result = request.invoke()
      if (result.isSuccessful) {
        return Result.success(result.body())
      } else {
        val errorResponse = ErrorUtils.parseError(result, retrofit)
        Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
      }
    } catch (e: Throwable) {
      Result.error("Unable to fetch data from network", null)
    }
  }
}