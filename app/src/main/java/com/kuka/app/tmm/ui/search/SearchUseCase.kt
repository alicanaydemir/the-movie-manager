package com.kuka.app.tmm.ui.search

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestSearch
import com.kuka.app.tmm.data.model.response.ResponseSearch
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseSearch, RequestSearch>() {
    override fun buildRequest(request: RequestSearch?): Flow<Resource<ResponseSearch>> {
        return repository.search(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}