package com.aargoncillo.component.itunesmovies.data.network

import com.aargoncillo.component.itunesmovies.data.network.model.MovieDto
import com.aargoncillo.component.itunesmovies.data.network.model.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

  @GET("search/")
  suspend fun getAllMovies(
    @Query("term") term: String? = "star",
    @Query("country") country: String? = "au",
    @Query("media") media: String? = "movie"
  ): Response<ResponseDto<MovieDto>>
}