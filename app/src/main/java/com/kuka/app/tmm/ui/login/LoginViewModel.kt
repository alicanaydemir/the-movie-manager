package com.kuka.app.tmm.ui.login

import androidx.lifecycle.viewModelScope
import com.kuka.app.tmm.core.BaseViewModel
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.SingleLiveEvent
import com.kuka.app.tmm.data.model.request.RequestCreateSession
import com.kuka.app.tmm.data.model.request.RequestCreateSessionWithLogin
import com.kuka.app.tmm.utils.helper.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val requestTokenUseCase: RequestTokenUseCase,
    private val createSessionWithLoginUseCase: CreateSessionWithLoginUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    val sharedHelper: SharedHelper
) : BaseViewModel() {
    val openWebsite = SingleLiveEvent<Boolean>()

    fun login(email: String, password: String) {
        requestToken(email, password, false)
    }

    fun loginWithWebSite() {
        requestToken(email = "", password = "", isWebsite = true)
    }

    private fun requestToken(email: String?, password: String?, isWebsite: Boolean?) {
        viewModelScope.launch {
            requestTokenUseCase.execute(null).collect {
                when (it) {
                    is Resource.Loading -> {
                        if (it.status) loading.value = it.status
                    }
                    is Resource.Success -> {
                        if (it.response.success == true) {
                            sharedHelper.putStringData(
                                Constants.Pref.REQUEST_TOKEN,
                                it.response.requestToken
                            )
                            if (isWebsite != true)
                                createSessionWithLogin(email, password)
                            else
                                openWebsite.postValue(true)

                        }
                    }
                    is Resource.Error -> {
                        loading.value = false
                    }
                }
            }
        }
    }

    private fun createSessionWithLogin(email: String?, password: String?) {
        viewModelScope.launch {
            val request = RequestCreateSessionWithLogin(
                email, password, sharedHelper.getStringData(
                    Constants.Pref.REQUEST_TOKEN,
                    ""
                ) ?: ""
            )

            createSessionWithLoginUseCase.execute(request).collect {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        sharedHelper.putStringData(
                            Constants.Pref.REQUEST_TOKEN,
                            it.response.requestToken
                        )
                        createSession()
                    }
                    is Resource.Error -> {
                        loading.value = false
                        error.value = it.errorResponse.statusMessage
                    }
                }
            }
        }
    }

    private fun createSession() {
        viewModelScope.launch {
            val request = RequestCreateSession(
                sharedHelper.getStringData(
                    Constants.Pref.REQUEST_TOKEN,
                    ""
                ) ?: ""
            )

            createSessionUseCase.execute(request).collect {
                when (it) {
                    is Resource.Loading -> {
                        if (!it.status) loading.value = it.status
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
                        loading.value = false
                        error.value = it.errorResponse.statusMessage
                    }
                }
            }
        }
    }
}