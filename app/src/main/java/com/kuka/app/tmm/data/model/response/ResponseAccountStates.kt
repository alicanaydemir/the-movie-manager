package com.kuka.app.tmm.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseAccountStates(
    @SerializedName("favorite")
    val favorite: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("rated")
    val rated: Boolean?,
    @SerializedName("watchlist")
    val watchlist: Boolean?
)