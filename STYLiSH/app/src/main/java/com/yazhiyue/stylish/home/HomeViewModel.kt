package com.yazhiyue.stylish.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.data.HomeItem
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.network.LoadApiStatus
import com.yazhiyue.stylish.network.StylishApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _homeItems = MutableLiveData<List<HomeItem>>((emptyList()))

    val homeItems: MutableLiveData<List<HomeItem>>
        get() = _homeItems

    // stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Product?>()

    val navigateToDetail: MutableLiveData<Product?>
        get() = _navigateToDetail

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        getMarketingHotsResult()
    }

    private fun getMarketingHotsResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            try {
                val result =
                    StylishApi.retrofitService.getMarketingHots()

                _status.value = LoadApiStatus.DONE

                for (data in result.hotsList!!) {
                    _homeItems.value = _homeItems.value?.plus(HomeItem.Title(data.title))

                    val products = data.products

                    for (product in products) {
                        if (products.indexOf(product) % 2 == 0) {
                            _homeItems.value = _homeItems.value?.plus(HomeItem.FullProduct(product))
                        } else {
                            _homeItems.value =
                                _homeItems.value?.plus(HomeItem.CollageProduct(product))
                        }
                    }
                }
            } catch (e: Exception) {
                _status.value = LoadApiStatus.ERROR
                Log.i("getHotsResultError", "$e.message")
                _homeItems.value = emptyList()
            }
            _refreshStatus.value = false
        }

    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getMarketingHotsResult()
        }
    }

    fun navigateToDetail(product: Product) {
        _navigateToDetail.value = product
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}