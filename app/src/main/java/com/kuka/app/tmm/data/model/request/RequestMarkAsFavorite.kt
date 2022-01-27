package com.kuka.app.tmm.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestMarkAsFavorite(
    @SerializedName("favorite")
    val favorite: Boolean?,
    @SerializedName("media_id")
    val mediaId: Int?,
    @SerializedName("media_type")
    val mediaType: String?,

    var sessionId: String?,
    var accountId: Int?,
)