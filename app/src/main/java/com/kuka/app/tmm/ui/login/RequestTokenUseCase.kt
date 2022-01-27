package com.kuka.app.tmm.ui.login

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.response.ResponseRequestToken
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RequestTokenUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseRequestToken, Nothing>() {
    override fun buildRequest(request: Nothing?): Flow<Resource<ResponseRequestToken>> {
        return repository.requestToken()
            .onStart { emit(Resource.Loading(true)) }
    }
}