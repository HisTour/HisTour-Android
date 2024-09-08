package com.startup.histour.data.dto.place

import com.google.gson.annotations.SerializedName

data class ResponsePlaceDto(
    @SerializedName("places")
    val places: List<ResponsePlace>?
)