package com.yazhiyue.stylish.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.component.ColorSquare
import com.yazhiyue.stylish.data.Color
import com.yazhiyue.stylish.data.HomeItem
import com.yazhiyue.stylish.detail.DetailGalleryAdapter
import com.yazhiyue.stylish.home.HomeAdapter

@BindingAdapter("homeItems")
fun bindRecyclerViewWithHomeItems(recyclerView: RecyclerView, homeItems: List<HomeItem>?) {
    homeItems?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
            )
            .into(imgView)
    }
}

@BindingAdapter("color")
fun drawColorSquare(imageView: ImageView, color: Color?) {
    color?.let {
        imageView.background = ColorSquare("#${it.code}", imageView.context)
    }
}