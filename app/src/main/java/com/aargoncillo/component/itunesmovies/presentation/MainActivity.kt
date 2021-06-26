package com.aargoncillo.component.itunesmovies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aargoncillo.component.itunesmovies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}






















