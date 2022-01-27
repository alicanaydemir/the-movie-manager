package com.kuka.app.tmm.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestAccountStates(
    @SerializedName("movie_id")
    val movieId: Int?,
    @SerializedName("session_id")
    val sessionId: String?
)