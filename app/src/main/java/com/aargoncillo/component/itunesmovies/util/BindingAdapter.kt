package com.aargoncillo.component.itunesmovies.util

import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
  val shimmer = Shimmer.AlphaHighlightBuilder()
    .setDuration(1800)
    .setBaseAlpha(0.7f)
    .setHighlightAlpha(0.6f)
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setAutoStart(true)
    .build()
  val shimmerDrawable = ShimmerDrawable().apply {
    setShimmer(shimmer)
  }

  Glide
    .with(this)
    .load(url)
    .placeholder(shimmerDrawable)
    .into(this)
}

@BindingAdapter("videoUrl")
fun VideoView.loadVideo(url: String?) {
  url?.let { link ->
    var mediaControls: MediaController? = null
    if (mediaControls == null) {
      // creating an object of media controller class
      mediaControls = MediaController(context)

      // set the anchor view for the video view
      mediaControls.setAnchorView(this)
    }
    // set the media controller for video view
    setMediaController(mediaControls)
    setVideoPath(link)
    requestFocus()

    setOnClickListener {
      this.rootView.play_image_view.visibility = View.GONE
      start()
    }

    setOnErrorListener { _, _, _ ->
      Toast.makeText(context, "An Error Occurred While Playing Video", Toast.LENGTH_LONG).show()
      false
    }
  }
}