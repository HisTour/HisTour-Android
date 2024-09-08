package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.character.ResponseCharacterDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.GET

interface CharacterApi {
    @GET("characters")
    suspend fun getCharacter(): BaseResponse<ResponseCharacterDto>
}