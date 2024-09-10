package com.startup.histour.data.dto.character

import com.google.gson.annotations.SerializedName
import com.startup.histour.core.extension.orZero
import com.startup.histour.presentation.model.CharacterModel

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
) {
    fun toCharacter(): CharacterModel =
        CharacterModel(
            id = id.orZero(),
            name = name.orEmpty(),
            comment = comment?.welcome.orEmpty(),
            commentColor = color?.comment.orEmpty(),
            backgroundStart = color?.backgroundStart.orEmpty(),
            backgroundEnd = color?.backgroundEnd.orEmpty(),
            description = description.orEmpty(),
            normalImageUrl = image?.normalImageUrl.orEmpty(),
            faceImageUrl = image?.faceImageUrl.orEmpty()
        )
}