package com.startup.histour.domain.repository

import com.startup.histour.data.dto.member.ResponseUserInfoDto
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun setUserCharacter(characterId: Int)
    suspend fun getUserInfo(): Flow<ResponseUserInfoDto>
}