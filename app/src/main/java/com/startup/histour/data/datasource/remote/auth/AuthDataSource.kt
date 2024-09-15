package com.startup.histour.data.datasource.remote.auth

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import com.startup.histour.data.datastore.ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.REFRESH_ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.TokenDataStoreProvider
import com.startup.histour.data.dto.auth.RequestLogin
import com.startup.histour.data.remote.api.AuthApi
import com.startup.histour.data.remote.api.LoginApi
import com.startup.histour.data.util.handleExceptionIfNeed
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthDataSource @Inject constructor(
    private val authApi: AuthApi, private val loginApi: LoginApi,
    private val tokenDataStoreProvider: TokenDataStoreProvider,
    @Named(ACCESS_TOKEN_KEY_NAME) private val accessTokenKey: Preferences.Key<String>,
    @Named(REFRESH_ACCESS_TOKEN_KEY_NAME) private val refreshTokenKey: Preferences.Key<String>
) {
    suspend fun login(requestLogin: RequestLogin): Boolean {
        return handleExceptionIfNeed {
            val response = loginApi.login(requestLogin.type, requestLogin.token)
            val body = response.body()
            if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_CREATED && body != null) {
                val (accessToken, refreshToken) = body.data
                tokenDataStoreProvider.putValue(accessTokenKey, accessToken.orEmpty())
                tokenDataStoreProvider.putValue(refreshTokenKey, refreshToken.orEmpty())
                true
            } else {
                false
            }
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
}