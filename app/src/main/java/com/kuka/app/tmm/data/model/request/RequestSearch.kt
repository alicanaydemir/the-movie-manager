package com.kuka.app.tmm.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestSearch(
    val page: Int?,
    val query: String?
) : Parcelable
