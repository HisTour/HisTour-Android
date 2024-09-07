package com.startup.histour.data.dto.character

import com.google.gson.annotations.SerializedName

data class ResponseColor(
    @SerializedName("backgroundEnd")
    val backgroundEnd: String?,
    @SerializedName("backgroundStart")
    val backgroundStart: String?,
    @SerializedName("comment")
    val comment: String?
)