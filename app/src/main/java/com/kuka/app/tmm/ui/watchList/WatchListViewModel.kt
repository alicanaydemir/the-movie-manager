package com.kuka.app.tmm.ui.watchList

import androidx.lifecycle.viewModelScope
import com.kuka.app.tmm.core.BaseViewModel
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.data.model.request.RequestGetWatchList
import com.kuka.app.tmm.data.model.response.Movie
import com.kuka.app.tmm.utils.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    val sharedHelper: SharedHelper,
    private val watchListUseCase: GetWatchListUseCase
) : BaseViewModel() {

    val movieList = MutableStateFlow<List<Movie?>?>(emptyList())

    fun getList() {
        viewModelScope.launch {
            val request = RequestGetWatchList(
                1,
                getSessionId(),
                getAccountId()
            )
            watchListUseCase.execute(request).collect {
                when (it) {
                    is Resource.Loading -> {
                        loading.value = it.status
                    }
                    is Resource.Success -> {
                        movieList.value = it.response.results
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
}