package com.startup.histour.data.dto.sse

data class ResponseEventSource(
    val status: EventSourceStatus,
    val msg: ResponseSSEData? = null
)

enum class EventSourceStatus {
    SUCCESS,
    ERROR,
    CLOSED,
    OPEN
}
