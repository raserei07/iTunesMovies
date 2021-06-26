package com.aargoncillo.component.itunesmovies.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
  @PrimaryKey var trackId: Int,
  var trackName: String? = null,
  var genre: String? = null,
  var price: String? = null,
  var artistName: String? = null,
  var imageUrl: String? = null,
  var releaseDate: String? = null,
  var longDescription: String? = null,
) : Parcelable