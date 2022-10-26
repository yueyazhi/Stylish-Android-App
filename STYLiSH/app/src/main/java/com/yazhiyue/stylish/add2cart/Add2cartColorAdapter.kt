package com.yazhiyue.stylish.add2cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yazhiyue.stylish.data.Color
import com.yazhiyue.stylish.databinding.ItemAdd2cartColorBinding

class Add2cartColorAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Color, Add2cartColorAdapter.ColorViewHolder>(DiffCallback) {

    private var selectedPos = -1

    class ColorViewHolder(private var binding: ItemAdd2cartColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected: Boolean = false

        fun bind(color: Color, selectedPos: Int) {

            binding.color = color

            binding.viewHolder = this

            isSelected = selectedPos == bindingAdapterPosition

            binding.executePendingBindings()

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
            ItemAdd2cartColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(color)
            selectedPos = holder.bindingAdapterPosition
            notifyDataSetChanged()
        }
        holder.bind(color, selectedPos)
    }


    class OnClickListener(val clickListener: (color: Color) -> Unit) {
        fun onClick(color: Color) = clickListener(color)
    }


}