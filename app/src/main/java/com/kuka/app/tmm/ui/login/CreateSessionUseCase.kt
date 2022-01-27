package com.kuka.app.tmm.ui.login

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.core.UseCase
import com.kuka.app.tmm.data.model.request.RequestCreateSession
import com.kuka.app.tmm.data.model.response.ResponseCreateSession
import com.kuka.app.tmm.data.source.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<ResponseCreateSession, RequestCreateSession>() {
    override fun buildRequest(request: RequestCreateSession?): Flow<Resource<ResponseCreateSession>> {
        return repository.createSession(request)
            .onStart { emit(Resource.Loading(true)) }
    }
}