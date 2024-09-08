package com.startup.histour.data.util

import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.Preferences
import com.startup.histour.data.datastore.REFRESH_ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.TokenDataStoreProvider
import com.startup.histour.presentation.login.ui.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class RefreshTokenAuthInterceptor @Inject constructor(
    private val context: Context,
    private val tokenDataStoreProvider: TokenDataStoreProvider,
    @Named(REFRESH_ACCESS_TOKEN_KEY_NAME) private val refreshTokenKey: Preferences.Key<String>,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            tokenDataStoreProvider.getValue(refreshTokenKey, "")
        }
        val request = chain.request()
            .newBuilder()
            .addHeaders(accessToken)
            .build()
        val response = chain.proceed(request)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            // 로그아웃
            CoroutineScope(Dispatchers.Main).launch {
                Intent(context, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(this)
                }
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