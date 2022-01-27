package com.kuka.app.tmm.ui.favorites

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestGetFavorite
import com.kuka.app.tmm.data.model.response.ResponseGetFavorite
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseGetFavorite, RequestGetFavorite>() {
    override fun buildRequest(request: RequestGetFavorite?): Flow<Resource<ResponseGetFavorite>> {
        return repository.getFavorite(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}