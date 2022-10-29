package com.yazhiyue.stylish.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.databinding.ItemPaymentFormBinding
import com.yazhiyue.stylish.databinding.ItemPaymentProductBinding

class PaymentAdapter(val viewModel: PaymentViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        viewModel.products.value?.let {
            return when (it.size) {
                0 -> 0
                else -> it.size + 1
            }
        }
        return 0
    }

    class ProductViewHolder(private var binding: ItemPaymentProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product?) {

            product?.let {
                binding.product = it
                binding.executePendingBindings()
            }
        }
    }

    class FormViewHolder(var binding: ItemPaymentFormBinding, val viewModel: PaymentViewModel) :
        RecyclerView.ViewHolder(binding.root), LifecycleOwner {

        // Set Spinner
        init {
            val spinnerAdapter = ArrayAdapter.createFromResource(
                binding.spinnerPaymentMethods.context,
                R.array.payment_method_list, android.R.layout.simple_spinner_item
            )
            spinnerAdapter.setDropDownViewResource(R.layout.item_payment_spinner)
            binding.spinnerPaymentMethods.adapter = spinnerAdapter
        }

        fun bind() {

            binding.viewModel = viewModel
            binding.lifecycleOwner = this
            binding.viewModel?.setupTpd(binding.formPaymentTpd)
            binding.executePendingBindings()
        }

        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        fun onAttach() {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }

        fun onDetach() {
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_PRODUCT -> ProductViewHolder(
                ItemPaymentProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM_VIEW_TYPE_FORM -> FormViewHolder(
                ItemPaymentFormBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                viewModel
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ProductViewHolder -> {
                val product = viewModel.products.value?.get(position)
                holder.bind(product)
            }
            is FormViewHolder -> {
                holder.bind()
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            viewModel.products.value?.size ?: 0 -> ITEM_VIEW_TYPE_FORM
            else -> ITEM_VIEW_TYPE_PRODUCT
        }
    }

    /**
     * It for [LifecycleRegistry] change [onViewAttachedToWindow]
     */
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        when (holder) {
            is FormViewHolder -> holder.onAttach()
        }
    }

    /**
     * It for [LifecycleRegistry] change [onViewDetachedFromWindow]
     */
    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is FormViewHolder -> holder.onDetach()
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_FORM = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT = 0x01
    }
}
