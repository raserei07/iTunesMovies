package com.aargoncillo.component.itunesmovies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aargoncillo.component.itunesmovies.R
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
  Glide
    .with(this)
    .load(url)
    .placeholder(R.drawable.ic_image_placeholder)
    .into(this)
}