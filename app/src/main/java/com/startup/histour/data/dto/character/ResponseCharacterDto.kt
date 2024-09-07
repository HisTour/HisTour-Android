package com.startup.histour.data.dto.character

import com.google.gson.annotations.SerializedName

data class ResponseCharacterDto(
    @SerializedName("characters")
    val characterResponses: List<ResponseCharacter>?
)