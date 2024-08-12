package com.startup.histour.data.dto.sse

data class ResponseEventSource(
    val status: EventSourceStatus,
    val msg: String? = null
)

enum class EventSourceStatus {
    SUCCESS,
    ERROR,
    NONE,
    CLOSED,
    OPEN
}
