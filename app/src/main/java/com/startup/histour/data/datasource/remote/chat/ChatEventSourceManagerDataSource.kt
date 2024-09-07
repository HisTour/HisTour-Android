package com.startup.histour.data.datasource.remote.chat

import com.startup.histour.annotation.SSEHttpClient
import com.startup.histour.data.dto.sse.ResponseEventSource
import com.startup.histour.data.util.EventSourceManager
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatEventSourceManagerDataSource @Inject constructor(
    @SSEHttpClient private val okHttpClient: OkHttpClient
) {
    fun connect(query: String): Flow<ResponseEventSource> {
        return EventSourceManager(okHttpClient).run {
            initEventSource(query)
            responseFlow
        }
    }
}
