package com.kuka.app.tmm.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val success = SingleLiveEvent<Boolean>()
    val empty = MutableLiveData<Boolean>()

    val error = SingleLiveEvent<String>()
}