package com.startup.histour.data.remote.dataource.chat

import com.startup.histour.data.util.EventSourceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatEventSourceManagerDataSource @Inject constructor(
    private val eventSourceManager: EventSourceManager
) {

    val sseStateFlow = eventSourceManager.responseFlow

    fun connect(query: String) {
        /* TODO 인코딩 필요한지 확인 */
        eventSourceManager.initEventSource(query)
    }

    fun cancel() {
        eventSourceManager.cancelEventSource()
    }
}
