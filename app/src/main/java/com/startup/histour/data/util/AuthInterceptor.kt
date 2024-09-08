package com.startup.histour.data.util

import androidx.datastore.preferences.core.Preferences
import com.startup.histour.data.datastore.ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.TokenDataStoreProvider
import com.startup.histour.domain.usecase.auth.RefreshTokenUpdateUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenDataStoreProvider: TokenDataStoreProvider,
    @Named(ACCESS_TOKEN_KEY_NAME) private val accessTokenKey: Preferences.Key<String>,
    private val refreshTokenUpdateUseCase: RefreshTokenUpdateUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            tokenDataStoreProvider.getValue(accessTokenKey, "")
        }
        val request = chain.request()
            .newBuilder()
            .addHeaders(accessToken)
            .build()
        val response = chain.proceed(request)
        return if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            refreshTokenUpdate(chain)
        } else {
            response
        }
    }

    private fun refreshTokenUpdate(
        chain: Interceptor.Chain,
    ): Response {
        runBlocking {
            refreshTokenUpdateUseCase.invoke()
        }
        val newToken = runBlocking { tokenDataStoreProvider.getValue(accessTokenKey, "") }
        val newRequest = chain.request().newBuilder()
            .removeHeader("Authorization")
            .addHeaders(newToken)
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private fun Request.Builder.addHeaders(token: String?) =
            this.apply { header("Authorization", BEARER + token.toString()) }

        private const val BEARER = "Bearer "
    }
}