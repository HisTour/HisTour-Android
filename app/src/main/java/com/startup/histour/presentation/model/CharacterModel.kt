package com.startup.histour.presentation.model

import com.startup.histour.UserInfo.CharacterInfo
import java.io.Serializable

data class CharacterModel(
    val id: Int,
    val name: String,
    val backgroundEnd: String,
    val backgroundStart: String,
    val commentColor: String,
    val comment: String,
    val description: String,
    val normalImageUrl: String,
    val faceImageUrl: String
) : Serializable {
    fun toCharacterInfo(): CharacterInfo =
        CharacterInfo.newBuilder()
            .setId(id.toString())
            .setName(name)
            .setDescription(comment)
            .setFaceImageUrl(faceImageUrl)
            .setNormalImageUrl(normalImageUrl)
            .build()

    companion object {
        fun orEmpty(): CharacterModel =
            CharacterModel(
                id = -1,
                name = "깨도사",
                comment = "",
                commentColor = "",
                backgroundStart = "",
                backgroundEnd = "",
                description = "",
                normalImageUrl = "https://histour-image.s3.ap-northeast-2.amazonaws.com/character/normal/character2.png",
                faceImageUrl = "https://histour-image.s3.ap-northeast-2.amazonaws.com/character/face/character2-face.png"
            )

        fun CharacterInfo?.toCharacterModel(): CharacterModel = this?.let {
            CharacterModel(
                id = id.toIntOrNull() ?: -1,
                name = name.orEmpty(),
                backgroundEnd = "",
                backgroundStart = "",
                commentColor = "",
                comment = description,
                description = description,
                normalImageUrl = normalImageUrl.orEmpty(),
                faceImageUrl = faceImageUrl.orEmpty()
            )
        } ?: orEmpty()
    }
}