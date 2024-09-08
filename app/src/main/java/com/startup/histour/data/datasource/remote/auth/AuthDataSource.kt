package com.startup.histour.data.datasource.remote.auth

import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.data.remote.api.AuthApi
import com.startup.histour.data.remote.api.LoginApi
import com.startup.histour.data.remote.api.TokenUpdateApi
import com.startup.histour.data.util.handleExceptionIfNeed
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSource @Inject constructor(private val authApi: AuthApi, private val loginApi: LoginApi, private val tokenUpdateApi: TokenUpdateApi) {
    suspend fun login(type: String): ResponseLoginDto {
        return handleExceptionIfNeed {
            loginApi.login(type).data
        }
    }

    suspend fun withdrawalAccount() {
        return handleExceptionIfNeed {
            authApi.withdrawalAccount()
        }
    }

    suspend fun logout() {
        return handleExceptionIfNeed {
            authApi.logout()
        }
    }

    suspend fun alignRefreshToken(): ResponseLoginDto {
        return handleExceptionIfNeed {
            tokenUpdateApi.alignRefreshToken().data
        }
    }
}