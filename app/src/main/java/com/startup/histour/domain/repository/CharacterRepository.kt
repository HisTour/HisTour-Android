package com.startup.histour.domain.repository

import com.startup.histour.data.dto.character.ResponseCharacterDto
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacter(): Flow<ResponseCharacterDto>
}