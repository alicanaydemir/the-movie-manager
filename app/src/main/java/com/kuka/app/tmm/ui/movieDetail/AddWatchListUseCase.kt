package com.kuka.app.tmm.ui.movieDetail

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestAddWatchList
import com.kuka.app.tmm.data.model.response.ResponseAddWatchList
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AddWatchListUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseAddWatchList, RequestAddWatchList>() {
    override fun buildRequest(request: RequestAddWatchList?): Flow<Resource<ResponseAddWatchList>> {
        return repository.addWatchList(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}