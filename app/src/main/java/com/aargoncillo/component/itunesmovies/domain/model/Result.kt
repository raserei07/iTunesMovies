package com.aargoncillo.component.itunesmovies.domain.model

/**
 * Class for holding success response, error response and loading status
 *
 * @param status identifier if the data is success, error, or loading
 * @param data contains the network responses when call is success
 * @param error formatted error which has response error code and error string body
 * @param message error message from response body
 *
 * @return properly formatted data based on [Status]
 */
data class Result<out T>(
  val status: Status,
  val data: T?,
  val error: Error?,
  val message: String?
) {

  enum class Status {
    SUCCESS,
    ERROR,
    LOADING
  }

  companion object {
    fun <T> success(data: T?): Result<T> {
      return Result(Status.SUCCESS, data, null, null)
    }

    fun <T> error(message: String?, error: Error?): Result<T> {
      return Result(Status.ERROR, null, error, message)
    }

    fun <T> loading(data: T? = null): Result<T> {
      return Result(Status.LOADING, data, null, null)
    }
  }

  override fun toString(): String {
    return "Result(status=$status, data=$data, error=$error, message=$message)"
  }
}