package com.yazhiyue.stylish.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.databinding.ItemCartBinding

class CartAdapter(val viewModel: CartViewModel) :
    ListAdapter<Product, CartAdapter.ProductViewHolder>(DiffCallback) {

    class ProductViewHolder(private var binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, viewModel: CartViewModel) {

            binding.product = product
            binding.viewModel = viewModel
            Log.i("product", "${product.title}")
            // forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Product]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return (oldItem.id == newItem.id) &&
                    (oldItem.selectedVariant.colorCode == newItem.selectedVariant.colorCode) &&
                    (oldItem.selectedVariant.size == newItem.selectedVariant.size)
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.quantity == newItem.quantity
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }
}