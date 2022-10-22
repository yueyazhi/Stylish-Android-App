package com.yazhiyue.stylish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yazhiyue.stylish.util.CurrentFragmentType

class MainViewModel : ViewModel() {

    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

}