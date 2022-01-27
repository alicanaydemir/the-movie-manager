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

    val totalItems = MutableStateFlow<Int?>(0)
    val movieList = MutableStateFlow<List<Movie?>?>(emptyList())
    var currentPage = 1

    private val query: MutableStateFlow<String?> = MutableStateFlow("")

    val text =
        query.flatMapLatest { txt ->
            loading.value = false
            if (txt.isNullOrEmpty()) {
                movieList.value = emptyList()
                emptyFlow()
            } else {
                currentPage = 1
                delay(500)
                val requestSearch = RequestSearch(1, txt)
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

                    }
                    is Resource.Success -> {
                        totalItems.value = it.response.totalResults
                        movieList.value = it.response.results
                    }
                    is Resource.Error -> {
                        movieList.value = emptyList()
                    }
                }
            }
        }
    }

    fun getList(page: Int) {
        viewModelScope.launch {
            val request = RequestSearch(page, query.value)
            searchUseCase.execute(request).collect {
                when (it) {
                    is Resource.Success -> {
                        val currentList: MutableList<Movie?> = mutableListOf()
                        movieList.value?.let {
                            currentList.addAll(it)
                        }
                        it.response.results?.let { list ->
                            currentList.addAll(list)
                            currentPage++
                        }
                        movieList.value = currentList
                        empty.value = currentList.isNullOrEmpty()
                    }
                    is Resource.Error -> {
                        movieList.value = emptyList()
                    }
                    else -> {}
                }
            }
        }
    }

}