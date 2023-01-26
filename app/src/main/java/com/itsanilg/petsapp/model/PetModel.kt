package com.itsanilg.petsapp.model

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.itsanilg.petsapp.R

data class PetsModel(
  val pets: List<PetModel>,

  )


data class PetModel(
  val image_url: String,
  val title: String,
  val content_url: String,
  val date_added: String,
) {
  //  @BindingAdapter("image_url")
  fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
      .load(imageUrl).apply(RequestOptions.noAnimation().placeholder(R.mipmap.ic_launcher))
      .into(view)
  }
}
