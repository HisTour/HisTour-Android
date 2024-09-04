package com.startup.histour.data.dto.sse

import com.google.gson.annotations.SerializedName


data class ResponseSSEData(
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("verbose")
    val verbose: String?
)