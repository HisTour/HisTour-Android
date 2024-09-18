package com.startup.histour.data.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import com.startup.histour.data.datastore.ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.TokenDataStoreProvider
import com.startup.histour.domain.usecase.auth.RefreshTokenUpdateUseCase
import com.startup.histour.presentation.login.ui.LoginActivity
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
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
        Log.e("LMH", "accessToken $accessToken")
        return if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()
            refreshTokenUpdate(chain, context)
        } else {
            response
        }
    }

    private fun refreshTokenUpdate(
        chain: Interceptor.Chain,
        context: Context,
    ): Response {
        runBlocking {
            runCatching { refreshTokenUpdateUseCase.invoke() }.getOrElse {
                Log.e("LMH", "error $it")
                Intent(context, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(this)
                }
            }
        }

        val newToken = runBlocking { tokenDataStoreProvider.getValue(accessTokenKey, "") }
        Log.e("LMH", "refreshTokenUpdate $newToken")
        val newRequest = chain.request().newBuilder()
            .removeHeader("Authorization")
            .addHeaders(newToken)
            .build()
        val response = chain.proceed(newRequest)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(this)
            }
        }
        return response

    }

    companion object {
        private fun Request.Builder.addHeaders(token: String?) =
            this.apply { header("Authorization", BEARER + token.toString()) }

        private const val BEARER = "Bearer "
    }
}