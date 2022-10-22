package com.yazhiyue.stylish.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.data.HomeItem
import com.yazhiyue.stylish.network.StylishApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _homeItems = MutableLiveData<List<HomeItem>>((emptyList()))

    val homeItems: LiveData<List<HomeItem>>
        get() = _homeItems

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        getMarketingHostsResult()
    }

    private fun getMarketingHostsResult() {
        coroutineScope.launch {
            val result =
                StylishApi.retrofitService.getMarketingHots()

            for (data in result.hotsList!!) {
                _homeItems.value = _homeItems.value?.plus(HomeItem.Title(data.title))

                val products = data.products

                for (product in products) {
                    if (products.indexOf(product) % 2 == 0) {
                        _homeItems.value = _homeItems.value?.plus(HomeItem.FullProduct(product))
                    } else {
                        _homeItems.value = _homeItems.value?.plus(HomeItem.CollageProduct(product))
                    }
                }
            }
        }

    }
}