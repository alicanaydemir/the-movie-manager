package com.kuka.app.tmm.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestSearch(
    @SerializedName("query") val query: String?
) : Parcelable
