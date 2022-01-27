package com.kuka.app.tmm.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestAddWatchList(
    @SerializedName("media_id")
    val mediaId: Int?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("watchlist")
    val watchlist: Boolean?,

    var sessionId: String?,
    var accountId: Int?,
)