package com.yazhiyue.stylish.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yazhiyue.stylish.add2cart.Add2cartViewModel
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.data.source.local.StylishDatabaseDao

class Add2cartViewModelFactory(private val product: Product,
                               private val databaseDao: StylishDatabaseDao) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Add2cartViewModel::class.java)) {
            return Add2cartViewModel(product, databaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}