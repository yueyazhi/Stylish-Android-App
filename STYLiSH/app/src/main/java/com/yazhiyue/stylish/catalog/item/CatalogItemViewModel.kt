package com.yazhiyue.stylish.catalog.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.catalog.CatalogTypeFilter
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.network.LoadApiStatus
import com.yazhiyue.stylish.network.StylishApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CatalogItemViewModel(catalogType: CatalogTypeFilter) : ViewModel() {

    private val _productList = MutableLiveData<List<Product>>(emptyList())

    val productList: LiveData<List<Product>>
        get() = _productList

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

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
            _status.value = LoadApiStatus.LOADING

            try {
                val result =
                    StylishApi.retrofitService.getProductList(type.value, nextPaging.toString())

                _status.value = LoadApiStatus.DONE

                _productList.value = _productList.value?.plus(result.data)
                nextPaging = result.nextPaging
            } catch (e: Exception) {
                _status.value = LoadApiStatus.ERROR
                Log.i("getHotsResultError", "$e.message")
                _productList.value = emptyList()
            }

            _refreshStatus.value = false
        }
    }

    fun loadNextPage() {
        if (nextPaging != null) {
            getProductList()
        }
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            _productList.value = emptyList()
            nextPaging = null
            getProductList()
        }
    }

}