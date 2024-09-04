package com.startup.histour.domain.usecase.chat

import android.util.Log
import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.dto.sse.EventSourceStatus
import com.startup.histour.data.dto.sse.ResponseEventSource
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.ChatEventSourceRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class ConnectEventSourceUseCase @Inject constructor(
    private val eventSourceRepository: ChatEventSourceRepository,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<ResponseEventSource, String>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: String): Flow<ResponseEventSource> = eventSourceRepository.connect(params)
}
