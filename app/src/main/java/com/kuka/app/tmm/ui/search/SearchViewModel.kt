package com.kuka.app.tmm.ui.search

import androidx.lifecycle.viewModelScope
import com.kuka.app.tmm.core.BaseViewModel
import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.data.model.request.RequestSearch
import com.kuka.app.tmm.data.model.response.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : BaseViewModel() {

    val movieList = MutableStateFlow<List<Movie?>?>(emptyList())

    private val query: MutableStateFlow<String?> = MutableStateFlow("")

    val text =
        query.flatMapLatest { txt ->
            loading.value = false
            if (txt.isNullOrEmpty()) {
                movieList.value = emptyList()
                emptyFlow()
            } else {
                delay(500)
                val requestSearch = RequestSearch(txt)
                searchUseCase.execute(requestSearch)
            }

        }.distinctUntilChanged()

    fun getResult(query: String?) {
        this.query.value = query
    }

    init {
        viewModelScope.launch {
            text.collect {
                when (it) {
                    is Resource.Loading -> {
                        //loading.value = it.status
                    }
                    is Resource.Success -> {
                        movieList.value = it.response.results
                    }
                    is Resource.Error -> {
                        movieList.value = emptyList()
                    }
                }
            }
        }
    }


}