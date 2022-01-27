package com.kuka.app.tmm.core

import com.kuka.app.tmm.data.model.response.ErrorResponse

sealed class Resource<out T> {
    class Success<T>(val response: T) : Resource<T>()
    class Error(val errorResponse: ErrorResponse) : Resource<Nothing>()
    class Loading(val status: Boolean) : Resource<Nothing>()
}