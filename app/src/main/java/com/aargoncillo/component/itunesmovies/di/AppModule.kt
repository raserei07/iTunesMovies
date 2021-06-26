package com.aargoncillo.component.itunesmovies.di

import android.content.Context
import com.aargoncillo.component.itunesmovies.data.local.dao.MovieDao
import com.aargoncillo.component.itunesmovies.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  /**
   * Provides reference for the apps [Context]
   *
   * @return context of app
   */
  @Singleton
  @Provides
  fun provideApplication(@ApplicationContext app: Context): BaseApplication {
    return app as BaseApplication
  }
}