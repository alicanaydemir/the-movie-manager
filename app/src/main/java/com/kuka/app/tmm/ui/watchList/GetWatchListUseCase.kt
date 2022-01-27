package com.kuka.app.tmm.ui.watchList

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestGetWatchList
import com.kuka.app.tmm.data.model.response.ResponseGetWatchList
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetWatchListUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseGetWatchList, RequestGetWatchList>() {
    override fun buildRequest(request: RequestGetWatchList?): Flow<Resource<ResponseGetWatchList>> {
        return repository.getWatchList(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}