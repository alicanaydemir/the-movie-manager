package com.kuka.app.tmm.ui.main

import androidx.lifecycle.viewModelScope
import com.kuka.app.tmm.core.BaseViewModel
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.SingleLiveEvent
import com.kuka.app.tmm.data.model.request.RequestAccount
import com.kuka.app.tmm.utils.helper.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val sharedHelper: SharedHelper
) : BaseViewModel() {

    val isLoadingSplash = MutableStateFlow(true)
    val restartApp = SingleLiveEvent<Boolean>()

    init {
        viewModelScope.launch {
            delay(1000)
            isLoadingSplash.value = false
        }
    }

    fun logout(){
        sharedHelper.putStringData(Constants.Pref.SESSION_ID,"")
        restartApp.value=true
    }

    fun account() {
        viewModelScope.launch {

            val requestAccount = RequestAccount(
                sharedHelper.getStringData(
                    Constants.Pref.SESSION_ID,
                    ""
                ) ?: ""
            )
            accountUseCase.execute(requestAccount).collect {
                when (it) {
                    is Resource.Loading -> {
                        loading.value = it.status
                    }
                    is Resource.Success -> {
                        it.response.id?.let { it1 ->
                            sharedHelper.putIntData(
                                Constants.Pref.ACCOUNT_ID,
                                it1
                            )
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

}