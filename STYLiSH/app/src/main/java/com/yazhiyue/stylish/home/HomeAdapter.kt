package com.yazhiyue.stylish.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yazhiyue.stylish.data.HomeItem
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.databinding.*


class HomeAdapter(private val onClickListener: OnClickListener) : ListAdapter<HomeItem,
        RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }

    class TitleViewHolder(private val binding: ItemHomeTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.title = title
            binding.executePendingBindings()
        }

    }

    class FullProductViewHolder(private val binding: ItemHomeFullBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, onClickListener: OnClickListener) {
            binding.product = product
            binding.root.setOnClickListener { onClickListener.onClick(product) }
            binding.executePendingBindings()
        }

    }

    class CollageProductViewHolder(private val binding: ItemHomeCollageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, onClickListener: OnClickListener) {
            binding.product = product
            binding.root.setOnClickListener { onClickListener.onClick(product) }
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_TITLE = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT_FULL = 0x01
        private const val ITEM_VIEW_TYPE_PRODUCT_COLLAGE = 0x02
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_TITLE -> TitleViewHolder(
                ItemHomeTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_PRODUCT_FULL -> FullProductViewHolder(
                ItemHomeFullBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_PRODUCT_COLLAGE -> CollageProductViewHolder(
                ItemHomeCollageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                holder.bind((getItem(position) as HomeItem.Title).title)
            }
            is FullProductViewHolder -> {
                holder.bind((getItem(position) as HomeItem.FullProduct).product, onClickListener)
            }
            is CollageProductViewHolder -> {
                holder.bind((getItem(position) as HomeItem.CollageProduct).product, onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeItem.Title -> ITEM_VIEW_TYPE_TITLE
            is HomeItem.FullProduct -> ITEM_VIEW_TYPE_PRODUCT_FULL
            is HomeItem.CollageProduct -> ITEM_VIEW_TYPE_PRODUCT_COLLAGE
        }
    }


}
