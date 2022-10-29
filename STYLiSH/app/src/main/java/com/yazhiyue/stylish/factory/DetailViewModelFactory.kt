package com.yazhiyue.stylish.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yazhiyue.stylish.data.Product
import com.yazhiyue.stylish.detail.DetailViewModel

class DetailViewModelFactory(private val product: Product) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(product) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}