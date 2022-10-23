package com.yazhiyue.stylish.catalog.item

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


    private fun getProductList() {
        coroutineScope.launch {

            val result =
                StylishApi.retrofitService.getProductList(type.value, nextPaging.toString())
            _productList.value = _productList.value?.plus(result.data)
            nextPaging = result.nextPaging
        }
    }

    fun loadNextPage() {
        if(nextPaging != null) {
            getProductList()
        }
    }

}