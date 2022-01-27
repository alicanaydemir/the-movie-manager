package com.kuka.app.tmm.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseGetWatchList(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Movie?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
) : Parcelable