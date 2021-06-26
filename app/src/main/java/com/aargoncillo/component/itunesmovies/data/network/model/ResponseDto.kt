package com.aargoncillo.component.itunesmovies.data.network.model

import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
  @SerializedName("resultCount")
  var resultCount: Int? = null,

  @SerializedName("results")
  var results: List<T>
)