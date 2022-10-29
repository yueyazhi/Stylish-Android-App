package com.yazhiyue.stylish.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yazhiyue.stylish.data.source.local.StylishDatabaseDao
import com.yazhiyue.stylish.payment.PaymentViewModel

class PaymentViewModelFactory(
    private val databaseDao: StylishDatabaseDao,
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(databaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

