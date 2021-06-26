package com.aargoncillo.component.itunesmovies.di

import android.content.Context
import androidx.room.Room
import com.aargoncillo.component.itunesmovies.data.local.AppDatabase
import com.aargoncillo.component.itunesmovies.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  /**
   * Provides reference of room database
   */
  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
    return Room.databaseBuilder(
      appContext,
      AppDatabase::class.java,
      "app.db"
    ).build()
  }

  /**
   * Provides database calls on [MovieDao]
   */
  @Provides
  fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
    return appDatabase.movieDao()
  }
}