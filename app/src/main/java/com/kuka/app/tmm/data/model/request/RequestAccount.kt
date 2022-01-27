package com.kuka.app.tmm.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestAccount(
    @SerializedName("sessionId") val sessionId: String?
) : Parcelable
