package com.startup.histour.domain.repository

import com.startup.histour.data.dto.auth.ResponseLoginDto
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(type: String): Flow<ResponseLoginDto>
    suspend fun withdrawalAccount()
    suspend fun logout()
    suspend fun alignRefreshToken(): ResponseLoginDto
}