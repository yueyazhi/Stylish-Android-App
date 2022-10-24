package com.yazhiyue.stylish.detail

import android.annotation.SuppressLint
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.yazhiyue.stylish.databinding.ItemDetailGalleryBinding


class DetailGalleryAdapter :
    RecyclerView.Adapter<DetailGalleryAdapter.ImageViewHolder>() {

    private var images: List<String>? = null

    class ImageViewHolder(private var binding: ItemDetailGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.imageUrl = imageUrl
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemDetailGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        images?.let {
            holder.bind(images!![position])
        }
    }

    override fun getItemCount(): Int {
        return images?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitImages(images: List<String>) {
        this.images = images
        notifyDataSetChanged()
    }
}

