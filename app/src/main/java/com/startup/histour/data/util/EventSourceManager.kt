package com.startup.histour.data.util

import android.net.Uri
import android.util.Log
import com.google.gson.Gson
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
import com.startup.histour.BuildConfig.SSE_SERVER_DOMAIN
import com.startup.histour.data.dto.sse.ResponseSSEData

class EventSourceManager(
    @SSEHttpClient private val client: OkHttpClient,
) {
    private val channel = Channel<ResponseEventSource>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val responseFlow = channel.receiveAsFlow()
    private val gson = Gson()

    private var eventSource: EventSource? = null

    private val listener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            Log.e("EventSource ::", "onOpen :: ${response.message}")
            Log.e("EventSource ::", "onOpen :: ${response.body?.string().orEmpty()}")
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
            kotlin.runCatching { gson.fromJson<ResponseSSEData>(data, ResponseSSEData::class.java) }.getOrNull().let {
                channel.trySend(ResponseEventSource(EventSourceStatus.SUCCESS, it))
            }
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            Log.e("EventSource ::", "failure :: ${t?.message.orEmpty()}")
            channel.trySend(ResponseEventSource(EventSourceStatus.ERROR))
        }
    }

    fun initEventSource(url: String) {
        //RequestSSE 래핑해서 사용 key-value 쌍을 위함
        Log.e("LMH", "CONNECT URL $url")
        eventSource = EventSources.createFactory(client)
            .newEventSource(
                request = Request.Builder()
                    .url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept", "text/event-stream")
                    .build(),
                listener = listener
            )
    }

    fun cancelEventSource() {
        eventSource?.cancel()
    }
}