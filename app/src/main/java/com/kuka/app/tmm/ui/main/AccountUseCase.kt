package com.kuka.app.tmm.ui.main

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestAccount
import com.kuka.app.tmm.data.model.response.ResponseAccount
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseAccount, RequestAccount>() {
    override fun buildRequest(request: RequestAccount?): Flow<Resource<ResponseAccount>> {
        return repository.account(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}