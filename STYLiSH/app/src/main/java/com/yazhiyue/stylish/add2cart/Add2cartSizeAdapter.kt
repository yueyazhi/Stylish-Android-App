package com.yazhiyue.stylish.add2cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yazhiyue.stylish.data.Variant
import com.yazhiyue.stylish.databinding.ItemAdd2cartSizeBinding


class Add2cartSizeAdapter(
    private val onClickListener: Add2cartSizeAdapter.OnClickListener,
) :
    ListAdapter<Variant, Add2cartSizeAdapter.SizeViewHolder>(DiffCallback) {

    var selectedPos = -1


    class SizeViewHolder(private var binding: ItemAdd2cartSizeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected: Boolean = false

        fun bind(variant: Variant, selectedPos: Int) {
            binding.variant = variant

            binding.viewHolder = this

            isSelected = selectedPos == bindingAdapterPosition && variant.stock > 0

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Variant>() {
        override fun areItemsTheSame(oldItem: Variant, newItem: Variant): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Variant, newItem: Variant): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        return SizeViewHolder(
            ItemAdd2cartSizeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val variant = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(variant)
            selectedPos = holder.bindingAdapterPosition
            notifyDataSetChanged()
        }
        holder.bind(variant, selectedPos)
    }

    class OnClickListener(val clickListener: (variant: Variant) -> Unit) {
        fun onClick(variant: Variant) = clickListener(variant)
    }

}