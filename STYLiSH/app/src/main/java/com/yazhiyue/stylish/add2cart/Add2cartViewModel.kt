package com.yazhiyue.stylish.add2cart

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.data.Color
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.data.Variant

class Add2cartViewModel(product: Product) : ViewModel() {

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

    val quantity = MutableLiveData<Int>()

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
