package com.aargoncillo.component.itunesmovies.data.network.mapper

import com.aargoncillo.component.itunesmovies.data.network.model.MovieDto
import com.aargoncillo.component.itunesmovies.domain.model.Movie

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

  override fun mapToDomainModel(model: MovieDto): Movie {
    return Movie(
      trackId = model.trackId,
      trackName = model.trackName,
      genre = model.primaryGenreName,
      price = "Rent $${model.trackHdPrice} / Buy $${model.trackHdRentalPrice}",
      artistName = model.artistName,
      imageUrl = resizeImageUrl(model.artworkUrl100),
      videoUrl = model.previewUrl,
      releaseDate = model.releaseDate,
      ratingAdvisory= model.contentAdvisoryRating,
      longDescription = model.longDescription,
    )
  }

  override fun mapFromDomainModel(domainModel: Movie): MovieDto {
    return MovieDto(
      trackId = domainModel.trackId,
      trackName = domainModel.trackName,
      primaryGenreName = domainModel.genre,
      artistName = domainModel.artistName,
      artworkUrl100 = domainModel.imageUrl,
      previewUrl = domainModel.videoUrl,
      releaseDate = domainModel.releaseDate,
      contentAdvisoryRating = domainModel.ratingAdvisory,
      longDescription = domainModel.longDescription
    )
  }

  private fun resizeImageUrl(url: String?): String? {
    return url?.let {
      return it.replace("100x100bb", "268x0w")
    }
  }

  fun toDomainList(initial: List<MovieDto>): List<Movie> {
    return initial.map { mapToDomainModel(it) }.sortedBy { it.trackName }
  }
}