package com.startup.histour.data.datasource.remote.character

import com.startup.histour.data.dto.character.ResponseCharacterDto
import com.startup.histour.data.remote.api.CharacterApi
import com.startup.histour.data.util.handleExceptionIfNeed
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDataSource @Inject constructor(private val characterApi: CharacterApi) {
    suspend fun getCharacter(): ResponseCharacterDto {
        return handleExceptionIfNeed {
            characterApi.getCharacter().data
        }
    }
}