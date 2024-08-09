package com.startup.histour.domain.repository

import com.startup.histour.data.dto.sse.ResponseEventSource
import kotlinx.coroutines.flow.Flow

interface ChatEventSourceRepository {

    suspend fun connect(query: String)

    fun getEventFlow(): Flow<ResponseEventSource>

    fun cancel()
}