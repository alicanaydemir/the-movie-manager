package com.kuka.app.tmm.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseMarkAsFavorite(
    @SerializedName("favorite")
    val favorite: Boolean?,
    @SerializedName("media_id")
    val mediaId: Int?,
    @SerializedName("media_type")
    val mediaType: String?
)