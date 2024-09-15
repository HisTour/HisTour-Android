package com.startup.histour.data.dto.place

import com.google.gson.annotations.SerializedName

data class RequestRecommendPlace(
    @SerializedName("content")
    val content: String
)
