package com.yazhiyue.stylish.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yazhiyue.stylish.catalog.CatalogTypeFilter
import com.yazhiyue.stylish.catalog.item.CatalogItemViewModel

class CatalogItemViewModelFactory(private val catalogType: CatalogTypeFilter):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogItemViewModel::class.java)) {
            return CatalogItemViewModel(catalogType) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}