package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.character.CharacterDataSource
import com.startup.histour.data.dto.character.ResponseCharacterDto
import com.startup.histour.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterDataSource: CharacterDataSource) : CharacterRepository {
    override suspend fun getCharacter(): Flow<ResponseCharacterDto> = flow {
        emit(characterDataSource.getCharacter())
    }
}