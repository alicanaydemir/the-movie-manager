package com.kuka.app.tmm.data.model.response


import com.google.gson.annotations.SerializedName

data class ResponseAddWatchList(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?
)