package com.yazhiyue.stylish.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yazhiyue.stylish.MainViewModel
import com.yazhiyue.stylish.cart.CartViewModel
import com.yazhiyue.stylish.data.source.local.StylishDatabaseDao

class ViewModelFactory(
    private val databaseDao: StylishDatabaseDao
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(databaseDao) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(databaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
