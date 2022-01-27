package com.kuka.app.tmm.ui.movieDetail

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestMarkAsFavorite
import com.kuka.app.tmm.data.model.response.ResponseMarkAsFavorite
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MarkAsFavoriteUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseMarkAsFavorite, RequestMarkAsFavorite>() {
    override fun buildRequest(request: RequestMarkAsFavorite?): Flow<Resource<ResponseMarkAsFavorite>> {
        return repository.markAsFavorite(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}