package com.yazhiyue.stylish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.data.source.local.StylishDatabaseDao
import com.yazhiyue.stylish.util.CurrentFragmentType

class MainViewModel(private val databaseDao: StylishDatabaseDao) : ViewModel() {

    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    val countInCart: LiveData<Int> = databaseDao.getProductCount()

}