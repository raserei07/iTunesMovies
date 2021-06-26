package com.aargoncillo.component.itunesmovies.di

import com.aargoncillo.component.itunesmovies.Config
import com.aargoncillo.component.itunesmovies.data.local.dao.MovieDao
import com.aargoncillo.component.itunesmovies.data.network.mapper.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val baseUrl = Config.BASE_URL

  /**
   * Provides reference for mapping data from [MovieDao] or [Movies]
   *
   * @return mapped data of [MovieDao] or [Movies]
   */
  @Singleton
  @Provides
  fun provideRecipeMapper(): MovieDtoMapper {
    return MovieDtoMapper()
  }

  /**
   * Provides reference for building a Retrofit call
   *
   * @return Retrofit build
   */
  @Singleton
  @Provides
  fun provideMovieService(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }
}