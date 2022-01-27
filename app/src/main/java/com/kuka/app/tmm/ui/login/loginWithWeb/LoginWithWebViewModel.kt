package com.kuka.app.tmm.ui.login.loginWithWeb

import androidx.lifecycle.viewModelScope
import com.kuka.app.tmm.core.BaseViewModel
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.data.model.request.RequestCreateSession
import com.kuka.app.tmm.ui.login.CreateSessionUseCase
import com.kuka.app.tmm.utils.helper.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginWithWebViewModel @Inject constructor(
    val sharedHelper: SharedHelper,
    private val createSessionUseCase: CreateSessionUseCase
) : BaseViewModel() {

    fun createSession() {
        viewModelScope.launch {
            loading.value = true
            delay(1000)
            val request = RequestCreateSession(
                sharedHelper.getStringData(
                    Constants.Pref.REQUEST_TOKEN,
                    ""
                ) ?: ""
            )

            createSessionUseCase.execute(request).collect {
                when (it) {
                    is Resource.Loading -> {
                        if(it.status.not())loading.value = it.status
                    }
                    is Resource.Success -> {
                        if (it.response.success == true) {
                            sharedHelper.putStringData(
                                Constants.Pref.SESSION_ID,
                                it.response.sessionId
                            )
                            success.value = true
                        }
                    }
                    is Resource.Error -> {
                        error.value = it.errorResponse.statusMessage
                    }
                }
            }
        }
    }

}