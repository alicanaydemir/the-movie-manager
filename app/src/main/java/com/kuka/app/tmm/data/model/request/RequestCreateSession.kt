package com.kuka.app.tmm.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestCreateSession(
    @SerializedName("request_token") val requestToken: String
) : Parcelable
