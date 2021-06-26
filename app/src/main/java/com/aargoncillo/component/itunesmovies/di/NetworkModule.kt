package com.aargoncillo.component.itunesmovies.di

import com.aargoncillo.component.itunesmovies.Config
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

  @Singleton
  @Provides
  fun provideRecipeMapper(): MovieDtoMapper {
    return MovieDtoMapper()
  }

  @Singleton
  @Provides
  fun provideMovieService(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }
}