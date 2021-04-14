package com.nayan.varun.util

import android.content.Context
import android.widget.ImageView
import com.nayan.varun.R
import com.squareup.picasso.Picasso

class ImageUtil(var context: Context) {
    fun loadImage(imageUrl: String, imageView: ImageView) {
        Picasso.with(context).load(imageUrl).placeholder(R.drawable.ic_place_holder).fit().into(imageView)
    }
}