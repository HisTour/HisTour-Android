package com.startup.histour.presentation.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val backgroundEnd: String,
    val backgroundStart: String,
    val commentColor: String,
    val comment: String,
    val description: String,
    val normalImageUrl: String,
    val smallImageUrl: String
) {
    companion object {
        fun orEmpty(): CharacterModel =
            CharacterModel(
                id = 0,
                name = "깨도사",
                comment = "",
                commentColor = "",
                backgroundStart = "",
                backgroundEnd = "",
                description = "",
                normalImageUrl = "https://histour-image.s3.ap-northeast-2.amazonaws.com/character/normal/character2.png",
                smallImageUrl = "https://histour-image.s3.ap-northeast-2.amazonaws.com/character/face/character2-face.png"
            )
    }
}