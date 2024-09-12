package com.startup.histour.data.dto.attraction

import com.google.gson.annotations.SerializedName
import com.startup.histour.presentation.bundle.model.Attraction

data class ResponseAttraction(
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?
) {
    fun toAttraction(): Attraction = Attraction(
        description = description.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        name = name.orEmpty()
    )
}