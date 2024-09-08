package com.startup.histour.data.dto.attraction

import com.google.gson.annotations.SerializedName

data class ResponseAttraction(
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?
)