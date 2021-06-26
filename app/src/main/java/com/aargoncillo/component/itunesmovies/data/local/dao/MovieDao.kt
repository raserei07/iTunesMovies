package com.aargoncillo.component.itunesmovies.data.local.dao

import androidx.room.*
import com.aargoncillo.component.itunesmovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
  @Query("SELECT * FROM movies ORDER BY trackName ASC")
  fun getMovieList(): List<Movie>?

  @Query("SELECT * FROM movies WHERE trackId = :trackId")
  fun getMovie(trackId: Int): Flow<Movie?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(plants: List<Movie>)

  @Delete
  fun deleteAll(movie: List<Movie>)
}