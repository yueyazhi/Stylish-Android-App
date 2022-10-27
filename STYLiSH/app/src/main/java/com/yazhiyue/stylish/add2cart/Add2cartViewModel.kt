package com.yazhiyue.stylish.add2cart

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.data.Color
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.data.Variant
import com.yazhiyue.stylish.data.source.local.StylishDatabaseDao
import kotlinx.coroutines.*

class Add2cartViewModel(product: Product, private val databaseDao: StylishDatabaseDao) :
    ViewModel() {

    private val _product = MutableLiveData<Product>().apply {
        value = product
    }

    val product: LiveData<Product>
        get() = _product


    val selectedColor = MutableLiveData<Color>()

    val selectedVariant = MutableLiveData<Variant?>()

    private val _variantsBySelectedColor = MutableLiveData<List<Variant>>()

    val variantsBySelectedColor: LiveData<List<Variant>>
        get() = _variantsBySelectedColor

    // Handle navigation to Added Success
    private val _navigateToAddedSuccess = MutableLiveData<Product?>()

    val navigateToAddedSuccess: LiveData<Product?>
        get() = _navigateToAddedSuccess

    // Handle navigation to Added Fail
    private val _navigateToAddedFail = MutableLiveData<Product?>()

    val navigateToAddedFail: LiveData<Product?>
        get() = _navigateToAddedFail

    val quantity = MutableLiveData<Int>()

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun insertToCart() {
        product.value?.let {
            coroutineScope.launch {
                selectedVariant.value?.apply {
                    it.selectedVariant = this
                    it.quantity = quantity.value
                    if (isProductInCart(it)) {
                        _navigateToAddedFail.value = it
                    } else {
                        insertProductInDatabase(it)
                        _navigateToAddedSuccess.value = it
                    }
                }
            }
        }
    }

    fun onAddedSuccessNavigated() {
        _navigateToAddedSuccess.value = null
    }

    fun onAddedFailNavigated() {
        _navigateToAddedFail.value = null
    }

    private suspend fun insertProductInDatabase(product: Product) {
        withContext(Dispatchers.IO) {
            databaseDao.insert(product)
        }
    }

    private suspend fun isProductInCart(product: Product): Boolean {
        return withContext(Dispatchers.IO) {
            databaseDao.get(
                product.id,
                product.selectedVariant.colorCode,
                product.selectedVariant.size
            ) != null
        }
    }


    fun selectColor(color: Color) {
        selectedVariant.value = null
        selectedColor.value = color
        getVariantsBySelectedColor()
    }

    private fun getVariantsBySelectedColor() {
        _variantsBySelectedColor.value =
            selectedColor.value?.let {
                product.value?.variants?.filter { variant ->
                    variant.colorCode == it.code
                }
            }
    }

    fun selectSize(variant: Variant) {
        quantity.value = 1
        selectedVariant.value = variant
    }

    @InverseMethod("convertIntToString")
    fun convertStringToInt(value: String): Int {
        return try {
            value.toInt().let {
                when (it) {
                    0 -> 1
                    else -> it
                }
            }
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertIntToString(value: Int): String {
        return value.toString()
    }

    fun increaseQuantity() {
        quantity.value = quantity.value?.plus(1)
    }

    fun decreaseQuantity() {
        quantity.value = quantity.value?.minus(1)
    }


}
