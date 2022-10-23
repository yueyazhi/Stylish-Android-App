package com.yazhiyue.stylish.catalog.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.catalog.CatalogTypeFilter
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.network.StylishApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CatalogItemViewModel(catalogType: CatalogTypeFilter) : ViewModel() {

    private val _productList = MutableLiveData<List<Product>>(emptyList())

    val productList: LiveData<List<Product>>
        get() = _productList

    var nextPaging: Int? = null

    //decide which type of catalog would get from api
    var type = catalogType

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        getProductList()
    }

    val result = mutableListOf<Product>()

    private fun getProductList() {
        coroutineScope.launch {

            val result =
                StylishApi.retrofitService.getProductList(type.value, nextPaging.toString())
            _productList.value = _productList.value?.plus(result.data)
            nextPaging = result.nextPaging
        }
    }

}