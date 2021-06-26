package com.aargoncillo.component.itunesmovies.util

import com.aargoncillo.component.itunesmovies.domain.model.Error
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

/**
 * parses error response body and network response code
 */
object ErrorUtils {

  /**
   * Handles network call error to a manageable format.
   *
   * @param response collect error body from response
   * @param retrofit collect error code from network call
   * @return formatted Error
   * @throws IOException if formatting response error fails
   */
  fun parseError(response: Response<*>, retrofit: Retrofit): Error? {
    val converter = retrofit.responseBodyConverter<Error>(Error::class.java, arrayOfNulls(0))
    return try {
      converter.convert(response.errorBody()!!)
    } catch (e: IOException) {
      Error()
    }
  }
}