package com.startup.histour.domain.repository

import com.startup.histour.data.dto.auth.RequestLogin
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(requestLogin: RequestLogin): Flow<Boolean>
    suspend fun withdrawalAccount()
    suspend fun logout()
}