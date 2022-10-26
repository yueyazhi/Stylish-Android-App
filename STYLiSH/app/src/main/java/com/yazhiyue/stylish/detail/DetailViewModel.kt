package com.yazhiyue.stylish.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.data.Product

class DetailViewModel(product: Product) : ViewModel() {

    private val _product = MutableLiveData<Product>().apply {
        value = product
    }

    val product: LiveData<Product>
        get() = _product

    // Handle leave detail
    private val _leaveDetail = MutableLiveData<Boolean>()

    val leaveDetail: LiveData<Boolean>
        get() = _leaveDetail

    // Handle navigation to Add2cart
    private val _navigateToAdd2cart = MutableLiveData<Product?>()

    val navigateToAdd2cart: LiveData<Product?>
        get() = _navigateToAdd2cart

    val productSizesText =
        when (_product.value?.sizes?.size) {
            1 -> _product.value!!.sizes.first()
            else -> "${_product.value!!.sizes.first()} - ${_product.value!!.sizes.last()}"
        }

    //Calculate total stocks
    val totalStocks = getStocks()

    private fun getStocks(): Int {
        var stocks = 0
        for (variants in product.value!!.variants) {
            stocks += variants.stock
        }
        return stocks
    }

    fun leaveDetail() {
        _leaveDetail.value = true
    }

    fun navigateToAdd2cart(product: Product) {
        _navigateToAdd2cart.value = product
    }

    fun onAdd2cartNavigated() {
        _navigateToAdd2cart.value = null
    }
}