package com.aargoncillo.component.itunesmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aargoncillo.component.itunesmovies.data.local.dao.MovieDao
import com.aargoncillo.component.itunesmovies.domain.model.Movie

@Database(
  entities = [
    Movie::class
  ],
  version = 3
)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
  abstract fun movieDao(): MovieDao
}