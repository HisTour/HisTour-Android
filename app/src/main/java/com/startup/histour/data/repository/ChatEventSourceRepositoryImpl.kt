package com.startup.histour.data.repository

import com.startup.histour.data.dto.sse.ResponseEventSource
import com.startup.histour.data.datasource.remote.sse.chat.ChatEventSourceManagerDataSource
import com.startup.histour.domain.repository.ChatEventSourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatEventSourceRepositoryImpl @Inject constructor(
    private val eventSourceManagerDataSource: ChatEventSourceManagerDataSource
) : ChatEventSourceRepository {

    override suspend fun connect(query: String): Flow<ResponseEventSource> {
        return eventSourceManagerDataSource.connect(query)
    }
}