package com.startup.histour.data.dto.attraction

import com.google.gson.annotations.SerializedName

data class ResponseAttractionDto(
    @SerializedName("attractions")
    val attractions: List<ResponseAttraction>?
)