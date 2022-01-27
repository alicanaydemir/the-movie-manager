package com.kuka.app.tmm.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseAccount(
    @SerializedName("avatar")
    val avatar: Avatar?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("include_adult")
    val includeAdult: Boolean?,
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?
) {
    data class Avatar(
        @SerializedName("gravatar")
        val gravatar: Gravatar?
    ) {
        data class Gravatar(
            @SerializedName("hash")
            val hash: String?
        )
    }
}