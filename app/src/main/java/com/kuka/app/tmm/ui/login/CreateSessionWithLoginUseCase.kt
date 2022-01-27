package com.kuka.app.tmm.ui.login

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestCreateSessionWithLogin
import com.kuka.app.tmm.data.model.response.ResponseCreateSessionWithLogin
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CreateSessionWithLoginUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseCreateSessionWithLogin, RequestCreateSessionWithLogin>() {
    override fun buildRequest(request: RequestCreateSessionWithLogin?): Flow<Resource<ResponseCreateSessionWithLogin>> {
        return repository.createSessionWithLogin(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}