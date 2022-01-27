package com.kuka.app.tmm.ui.movieDetail

import androidx.lifecycle.viewModelScope
import com.kuka.app.tmm.core.BaseViewModel
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.data.model.request.RequestAccountStates
import com.kuka.app.tmm.data.model.request.RequestAddWatchList
import com.kuka.app.tmm.data.model.request.RequestMarkAsFavorite
import com.kuka.app.tmm.data.model.response.ResponseAccountStates
import com.kuka.app.tmm.utils.helper.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val markAsFavoriteUseCase: MarkAsFavoriteUseCase,
    private val addWatchListUseCase: AddWatchListUseCase,
    private val accountStateUseCase: AccountStateUseCase,
    val sharedHelper: SharedHelper
) : BaseViewModel() {

    val accountStates =
        MutableStateFlow(ResponseAccountStates(false, 0, null, false))

    fun markAsFavorite() {
        viewModelScope.launch {
            val requestMarkAsFavorite = RequestMarkAsFavorite(
                accountStates.value.favorite?.not(),
                accountStates.value.id,
                "movie",
                getSessionId(),
                getAccountId()
            )
            markAsFavoriteUseCase.execute(requestMarkAsFavorite).collect {
                when (it) {
                    is Resource.Loading -> {
                        loading.value = it.status
                    }
                    is Resource.Success -> {
                        getAccountState(
                            accountStates.value.id ?: 0
                        )
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    fun addWatchList() {
        viewModelScope.launch {
            val requestAddWatchList = RequestAddWatchList(
                accountStates.value.id,
                "movie",
                accountStates.value.watchlist?.not(),
                getSessionId(),
                getAccountId()
            )
            addWatchListUseCase.execute(requestAddWatchList).collect {
                when (it) {
                    is Resource.Loading -> {
                        loading.value = it.status
                    }
                    is Resource.Success -> {
                        getAccountState(
                            accountStates.value.id ?: 0
                        )
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun getSessionId(): String {
        return sharedHelper.getStringData(
            Constants.Pref.SESSION_ID,
            ""
        ) ?: ""
    }

    private fun getAccountId(): Int {
        return sharedHelper.getIntData(
            Constants.Pref.ACCOUNT_ID,
            0
        ) ?: 0
    }

    fun getAccountState(mediaId: Int) {
        viewModelScope.launch {
            val requestAccountStates = RequestAccountStates(mediaId, getSessionId())
            accountStateUseCase.execute(requestAccountStates).collect {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        accountStates.value = it.response
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }
}