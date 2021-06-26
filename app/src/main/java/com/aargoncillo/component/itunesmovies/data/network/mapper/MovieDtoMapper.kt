package com.aargoncillo.component.itunesmovies.data.network.mapper

import com.aargoncillo.component.itunesmovies.data.network.model.MovieDto
import com.aargoncillo.component.itunesmovies.domain.model.Movie

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

  override fun mapToDomainModel(model: MovieDto): Movie {
    return Movie(
      trackId = model.trackId,
      trackName = model.trackName,
      genre = model.primaryGenreName,
      price = "$${model.trackPrice}",
      artistName = model.artistName,
      imageUrl = model.artworkUrl100,
      releaseDate = model.releaseDate,
      longDescription = model.longDescription,
    )
  }

  override fun mapFromDomainModel(domainModel: Movie): MovieDto {
    return MovieDto(
      trackId = domainModel.trackId,
      trackName = domainModel.trackName,
      primaryGenreName = domainModel.genre,
      trackPrice = domainModel.price?.toDouble(),
      artistName = domainModel.artistName,
      artworkUrl100 = domainModel.imageUrl,
      releaseDate = domainModel.releaseDate,
      longDescription = domainModel.longDescription
    )
  }

  fun toDomainList(initial: List<MovieDto>): List<Movie> {
    return initial.map { mapToDomainModel(it) }.sortedBy { it.trackName }
  }
}