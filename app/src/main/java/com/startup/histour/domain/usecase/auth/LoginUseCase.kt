package com.startup.histour.domain.usecase.auth

import androidx.datastore.preferences.core.Preferences
import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.datastore.ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.REFRESH_ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.TokenDataStoreProvider
import com.startup.histour.data.dto.auth.ResponseLoginDto
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.AuthRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataStoreProvider: TokenDataStoreProvider,
    @Named(ACCESS_TOKEN_KEY_NAME) private val accessTokenKey: Preferences.Key<String>,
    @Named(REFRESH_ACCESS_TOKEN_KEY_NAME) private val refreshTokenKey: Preferences.Key<String>,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<ResponseLoginDto, String>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: String): Flow<ResponseLoginDto> = authRepository.login(params).onEach {
        val (accessToken, refreshToken) = it
        tokenDataStoreProvider.putValue(accessTokenKey, accessToken.orEmpty())
        tokenDataStoreProvider.putValue(refreshTokenKey, refreshToken.orEmpty())
    }
}