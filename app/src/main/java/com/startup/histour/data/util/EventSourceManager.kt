package com.startup.histour.data.util

import android.util.Log
import com.startup.histour.annotation.SSEHttpClient
import com.startup.histour.data.dto.sse.EventSourceStatus
import com.startup.histour.data.dto.sse.ResponseEventSource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventSourceManager @Inject constructor(
    @SSEHttpClient private val client: OkHttpClient,
) {
    private val channel = Channel<ResponseEventSource>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val responseFlow = channel.receiveAsFlow()

    private var eventSource: EventSource? = null

    private val listener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            channel.trySend(ResponseEventSource(EventSourceStatus.OPEN))
        }

        override fun onClosed(eventSource: EventSource) {
            super.onClosed(eventSource)
            Log.e("EventSource ::", "closed :: $eventSource")
            channel.trySend(ResponseEventSource(EventSourceStatus.CLOSED))
        }

        override fun onEvent(eventSource: EventSource, id: String?, type: String?, data: String) {
            super.onEvent(eventSource, id, type, data)
            Log.e("EventSource ::", "event :: $data")
            channel.trySend(ResponseEventSource(EventSourceStatus.SUCCESS, data))
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            Log.e("EventSource ::", "failure :: ${t?.message.orEmpty()}")
            channel.trySend(ResponseEventSource(EventSourceStatus.ERROR))
        }
    }

    fun initEventSource(query: String) {
        //RequestSSE 래핑해서 사용 key-value 쌍을 위함
        eventSource = EventSources.createFactory(client)
            .newEventSource(
                request = Request.Builder()
                    .url("")
                    .header("Accept", "application/json")
                    .addHeader("Accept", "text/event-stream")
                    .build(),
                /*TODO Header 체크 필요 */
                /*TODO URL 체크 필요, url 에 query 보내는 지 */
                listener = listener
            )
    }

    fun cancelEventSource() {
        eventSource?.cancel()
    }
    companion object {
        fun create(
            client: OkHttpClient,
        ): EventSourceManager = EventSourceManager(client)
    }
}
