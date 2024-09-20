package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.sse.chat.ChatEventSourceManagerDataSource
import com.startup.histour.data.dto.sse.RequestGetUrl
import com.startup.histour.data.dto.sse.ResponseEventSource
import com.startup.histour.data.remote.api.ChatApi
import com.startup.histour.data.util.handleExceptionIfNeed
import com.startup.histour.domain.repository.ChatEventSourceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatEventSourceRepositoryImpl @Inject constructor(
    private val eventSourceManagerDataSource: ChatEventSourceManagerDataSource,
    private val chatApi: ChatApi
) : ChatEventSourceRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun connect(request: RequestGetUrl): Flow<ResponseEventSource> = flow {
        val url = handleExceptionIfNeed {
            chatApi.getUrl(request).data.url
        }
        if (url == null) {
            throw NullPointerException()
        }
        emit(url)
    }.map { url ->
        eventSourceManagerDataSource.connect(url)
    }.flattenConcat()
}