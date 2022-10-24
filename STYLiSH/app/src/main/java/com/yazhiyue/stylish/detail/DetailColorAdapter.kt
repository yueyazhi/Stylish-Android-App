package com.yazhiyue.stylish.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yazhiyue.stylish.data.Color
import com.yazhiyue.stylish.databinding.ItemDetailColorBinding


class DetailColorAdapter :
    ListAdapter<Color, DetailColorAdapter.ColorViewHolder>(DiffCallback) {

    class ColorViewHolder(private var binding: ItemDetailColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: com.yazhiyue.stylish.data.Color) {
            color.let {
                binding.color = it
                binding.executePendingBindings()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Color>() {
        override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            ItemDetailColorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}