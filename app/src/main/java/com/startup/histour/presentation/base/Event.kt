package com.startup.histour.presentation.base


interface BaseEvent {
    object None : BaseEvent
    data class NotHandlingExceptionDelivery(val msg: String) : BaseEvent
}
interface Event : BaseEvent
interface NavigationEvent : BaseEvent {
    object Back : NavigationEvent
}
interface ScreenNavigationEvent : BaseEvent