package com.kuka.app.tmm.core

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class UseCase<M, Request> {

    abstract fun buildRequest(request: Request?): Flow<Resource<M>>

    fun execute(request: Request? = null) =
            buildRequest(request).catch {
                delay(250)
                emit(Resource.Loading(false))
                it.message?.let {
                    ///emit(Resource.Error(ErrorResponse(listOf(it))))
                }
                it.message?.let { msg -> Log.i("try-catch", msg) }
            }

}
