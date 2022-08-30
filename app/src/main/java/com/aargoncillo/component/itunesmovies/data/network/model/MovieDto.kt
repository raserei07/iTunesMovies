package com.aargoncillo.component.itunesmovies.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(

  @SerializedName("trackId")
  var trackId: Int,

  @SerializedName("trackName")
  var trackName: String? = null,

  @SerializedName("primaryGenreName")
  var primaryGenreName: String? = null,

  @SerializedName("trackHdPrice")
  var trackHdPrice: Double? = null,

  @SerializedName("trackHdRentalPrice")
  var trackHdRentalPrice: Double? = null,

  @SerializedName("artistName")
  var artistName: String? = null,

  @SerializedName("artworkUrl100")
  var artworkUrl100: String? = null,

  @SerializedName("previewUrl")
  var previewUrl: String? = null,

  @SerializedName("releaseDate")
  var releaseDate: String? = null,

  @SerializedName("contentAdvisoryRating")
  var contentAdvisoryRating: String? = null,

  @SerializedName("longDescription")
  var longDescription: String? = null,
)