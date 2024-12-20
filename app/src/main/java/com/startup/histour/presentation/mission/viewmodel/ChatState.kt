package com.startup.histour.presentation.mission.viewmodel

import com.startup.histour.presentation.base.Event
import com.startup.histour.presentation.base.State
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class Author {
    CHAT_BOT, USER
}

sealed interface ChatViewModelEvent : Event {
    data class SendMessage(val msg: String, val taskId: Int) : ChatViewModelEvent
}

data class ChatMessage(
    val id: Long,
    val author: Author,
    val message: String,
    val time: Long,
    val isLoading: Boolean = false,
    val taskId: String = "",
    val missionId: String = "",
    val placeId: String = "",
    val isDummy: Boolean = false
)

interface ChatViewState : State {
    val chatList: StateFlow<List<ChatMessage>>
    val userInfo : StateFlow<UserInfoModel>
    fun getNextChatId(): Long
}

class ChatViewStateImpl(override val userInfo: StateFlow<UserInfoModel>) : ChatViewState {
    override val chatList: MutableStateFlow<List<ChatMessage>> = MutableStateFlow(emptyList())
    override fun getNextChatId(): Long = (chatList.value.lastOrNull()?.id ?: 0L) + 1L
}