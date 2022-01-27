package com.kuka.app.tmm.ui.movieDetail

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestAccountStates
import com.kuka.app.tmm.data.model.response.ResponseAccountStates
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AccountStateUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseAccountStates, RequestAccountStates>() {
    override fun buildRequest(request: RequestAccountStates?): Flow<Resource<ResponseAccountStates>> {
        return repository.getAccountStates(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}