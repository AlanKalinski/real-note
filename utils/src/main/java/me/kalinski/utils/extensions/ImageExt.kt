package me.kalinski.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImageCenter(imageUrl: String?) {
    Glide
            .with(context)
            .load(imageUrl)
            .fitCenter()
            /*.placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)*/
            .crossFade()
            .into(this)
}

fun ImageView.loadImageFill(imageUrl: String?) {
    Glide
            .with(context)
            .load(imageUrl)
            .centerCrop()
            /*.placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)*/
            .crossFade()
            .into(this)
}