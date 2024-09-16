package com.startup.histour.domain.usecase.auth

import androidx.datastore.preferences.core.Preferences
import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.datastore.ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.REFRESH_ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.TokenDataStoreProvider
import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.data.remote.api.TokenUpdateApi
import com.startup.histour.data.util.handleExceptionIfNeed
import com.startup.histour.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class RefreshTokenUpdateUseCase @Inject constructor(
    private val tokenUpdateApi: TokenUpdateApi,
    private val tokenDataStoreProvider: TokenDataStoreProvider,
    @Named(ACCESS_TOKEN_KEY_NAME) private val accessTokenKey: Preferences.Key<String>,
    @Named(REFRESH_ACCESS_TOKEN_KEY_NAME) private val refreshTokenKey: Preferences.Key<String>,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<ResponseLoginDto, Unit>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    suspend operator fun invoke() {
        val response = handleExceptionIfNeed {
            tokenUpdateApi.alignRefreshToken().data
        }
        val (accessToken, refreshToken) = response
        tokenDataStoreProvider.putValue(accessTokenKey, accessToken.orEmpty())
        tokenDataStoreProvider.putValue(refreshTokenKey, refreshToken.orEmpty())
    }

    // 사용 안함
    override suspend fun buildUseCase(params: Unit): Flow<ResponseLoginDto> = flow { }
}