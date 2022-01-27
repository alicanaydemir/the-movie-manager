package com.kuka.app.tmm.utils.extensions

import com.google.gson.Gson
import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.data.model.response.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Response

fun ResponseBody.convertErrorBody(): ErrorResponse {
    return Gson().fromJson(this.string(), ErrorResponse().javaClass)
}

//fun Int.isCode401(): Boolean = this == 401

fun <M : Any> Response<M>.filterResponse(): Resource<M> {
    return when (this.isSuccessful) {
        true -> {
            this.body()?.let {
                Resource.Success(it)

            } ?: Resource.Error(ErrorResponse())
        }
        false -> {

            try {
                val error = this.errorBody()?.convertErrorBody()
                Resource.Error(error!!)

            } catch (e: Exception) {
                Resource.Error(ErrorResponse())
            }
        }
    }
}
