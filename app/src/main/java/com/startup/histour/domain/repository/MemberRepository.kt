package com.startup.histour.domain.repository

import com.startup.histour.data.dto.member.ResponseUserInfoDto
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun setUserProfile(characterId: Int, userName: String)
    suspend fun getUserInfo(): Flow<ResponseUserInfoDto>
}