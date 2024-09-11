package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.auth.AuthDataSource
import com.startup.histour.data.dto.auth.RequestLogin
import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) : AuthRepository {

    override suspend fun login(requestLogin: RequestLogin): Flow<Boolean> = flow {
        emit(authDataSource.login(requestLogin))
    }

    override suspend fun withdrawalAccount() {
        authDataSource.withdrawalAccount()
    }

    override suspend fun logout() {
        authDataSource.logout()
    }
}