package com.kuka.app.tmm.data.model.request

data class RequestGetFavorite(
    val page: Int?,
    var sessionId: String?,
    var accountId: Int?,
)