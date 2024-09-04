package com.startup.histour.presentation.mission.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.core.extension.safeLet
import com.startup.histour.data.dto.sse.EventSourceStatus
import com.startup.histour.domain.usecase.chat.ConnectEventSourceUseCase
import com.startup.histour.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val connectEventSourceUseCase: ConnectEventSourceUseCase,
) : BaseViewModel() {
    private val _state = ChatViewStateImpl()
    override val state: ChatViewState = _state

    private val viewModelEvent: MutableSharedFlow<ChatViewModelEvent> = MutableSharedFlow(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            viewModelEvent.collect(::handleViewModelEvent)
        }
    }

    fun notifyViewModelEvent(chatViewModelEvent: ChatViewModelEvent) {
        viewModelScope.launch {
            viewModelEvent.emit(chatViewModelEvent)
        }
    }

    private fun handleViewModelEvent(chatViewModelEvent: ChatViewModelEvent) {
        when (chatViewModelEvent) {
            is ChatViewModelEvent.SendMessage -> {
                _state.chatList.update { it + ChatMessage(id = (it.lastOrNull()?.id ?: 0) + 1, author = Author.USER, message = chatViewModelEvent.msg, time = System.currentTimeMillis()) }
                val chatId = (_state.chatList.value.lastOrNull()?.id ?: 0) + 1
                connectEventSourceUseCase.executeOnViewModel(
                    params = chatViewModelEvent.msg,
                    onEach = { response ->
                        when (response.status) {
                            EventSourceStatus.OPEN -> {
                                _state.chatList.update { it + ChatMessage(id = chatId, author = Author.CHAT_BOT, message = "", time = System.currentTimeMillis(), isLoading = true) }
                            }

                            EventSourceStatus.CLOSED -> {
                                connectEventSourceUseCase.cancelJob()
                            }

                            EventSourceStatus.ERROR -> {
                                val list = _state.chatList.value
                                list.findLast { it.id == chatId }?.let {
                                    _state.chatList.update { list -> list.map { if (it.id == chatId) it.copy(message = "잘 모르겠어요..!", isLoading = false) else it } }
                                }
                            }

                            EventSourceStatus.SUCCESS -> {
                                response.msg?.let { msg ->
                                    safeLet(msg.type, msg.contents) { type, contents ->
                                        if (type == "model_output") {
                                            val list = _state.chatList.value
                                            list.findLast { it.id == chatId }?.let {
                                                _state.chatList.update { list -> list.map { if (it.id == chatId) it.copy(message = contents, isLoading = false) else it } }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Log.e("LMH", "RESPONSE $response")
                    },
                    onError = {
                        Log.e("LMH", "STOP?? $it")
                    },
                )
            }
        }
    }
}