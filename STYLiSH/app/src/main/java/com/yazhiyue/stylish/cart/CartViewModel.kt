package com.yazhiyue.stylish.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.data.source.local.StylishDatabaseDao
import kotlinx.coroutines.*

class CartViewModel(private val databaseDao: StylishDatabaseDao) : ViewModel() {

    var products = databaseDao.getAllProduct()

    // Handle navigation to payment
    private val _navigateToPayment = MutableLiveData<Boolean?>()

    val navigateToPayment: MutableLiveData<Boolean?>
        get() = _navigateToPayment

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun navigateToPayment() {
        _navigateToPayment.value = true
    }

    fun onPaymentNavigated() {
        _navigateToPayment.value = null
    }


    /**
     * Remove the given [Product] from Room database
     */
    fun removeProduct(product: Product) {
        coroutineScope.launch {
            removeProductInCart(product)
        }
    }

    private suspend fun removeProductInCart(product: Product) {
        withContext(Dispatchers.IO) {
            databaseDao.delete(
                product.id,
                product.selectedVariant.colorCode,
                product.selectedVariant.size
            )
        }
    }


    /**
     * Update the given [Product] from Room database
     */
    private fun updateProduct(product: Product) {
        product.quantity?.let { quantity ->
            product.selectedVariant.let {
                if (quantity in 1..it.stock) {
                    coroutineScope.launch {
                        updateProductInCart(product)
                    }
                }
            }
        }
    }

    private suspend fun updateProductInCart(product: Product) {
        withContext(Dispatchers.IO) {
            databaseDao.update(product)
        }
    }

    /**
     * Update the given [Product] with new amount
     */
    fun increaseAmount(product: Product) {
        product.quantity = product.quantity?.plus(1)
        updateProduct(product)
    }

    /**
     * Update the given [Product] with new amount
     */
    fun decreaseAmount(product: Product) {
        product.quantity = product.quantity?.minus(1)
        updateProduct(product)
    }


}