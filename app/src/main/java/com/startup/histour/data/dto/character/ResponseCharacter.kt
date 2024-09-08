package com.startup.histour.data.dto.character

import com.google.gson.annotations.SerializedName

data class ResponseCharacter(
    @SerializedName("color")
    val color: ResponseColor?,
    @SerializedName("comment")
    val comment: ResponseComment?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: ResponseImage?,
    @SerializedName("name")
    val name: String?
)