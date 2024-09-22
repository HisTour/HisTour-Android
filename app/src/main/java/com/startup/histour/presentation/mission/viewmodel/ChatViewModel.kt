package com.startup.histour.presentation.mission.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.startup.histour.R
import com.startup.histour.core.extension.safeLet
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.data.dto.sse.EventSourceStatus
import com.startup.histour.data.dto.sse.RequestGetUrl
import com.startup.histour.data.remote.api.ChatApi
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.usecase.chat.ConnectEventSourceUseCase
import com.startup.histour.presentation.base.BaseViewModel
import com.startup.histour.presentation.model.UserInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val connectEventSourceUseCase: ConnectEventSourceUseCase,
    private val userInfoDataStoreProvider: UserInfoDataStoreProvider,
    @ApplicationContext context: Context,
) : BaseViewModel() {
    private val _state = ChatViewStateImpl(
        userInfo = userInfoDataStoreProvider.getUserInfoFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserInfoModel.orEmpty()
        )
    )
    override val state: ChatViewState = _state

    private val viewModelEvent: MutableSharedFlow<ChatViewModelEvent> = MutableSharedFlow(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            _state.chatList.update {
                listOf(
                    ChatMessage(
                        id = 0L,
                        message = context.getString(R.string.chatting_intro_message, userInfoDataStoreProvider.getUserInfo().userName),
                        author = Author.CHAT_BOT,
                        time = System.currentTimeMillis(),
                        isDummy = true
                    )
                )
            }
        }
        viewModelScope.launch {
            viewModelEvent.collect(::handleViewModelEvent)
        }
    }

    fun canSend(): Boolean = !connectEventSourceUseCase.isJobRunning()

    fun notifyViewModelEvent(chatViewModelEvent: ChatViewModelEvent) {
        viewModelScope.launch {
            viewModelEvent.emit(chatViewModelEvent)
        }
    }

    private fun handleViewModelEvent(chatViewModelEvent: ChatViewModelEvent) {
        when (chatViewModelEvent) {
            is ChatViewModelEvent.SendMessage -> {
                val userNewChatId = _state.getNextChatId()
                Log.e("LMH", "NEXTCHATID $userNewChatId")
                val botChatId = userNewChatId + 1
                Log.e("LMH", "NEXTCHATID Bot $botChatId")
                viewModelScope.launch {
                    _state.chatList.update { it + ChatMessage(id = (it.lastOrNull()?.id ?: 0) + 1, author = Author.USER, message = chatViewModelEvent.msg, time = System.currentTimeMillis()) }
                    connectEventSourceUseCase.executeOnViewModel(
                        launchPolicy = BaseUseCase.LaunchPolicy.RUN_EXIST_JOB_IF_LAUNCHED,
                        params = RequestGetUrl(
                            qa = _state.chatList.value.filterNot { it.isDummy }.map { it.message },
                            characterId = _state.userInfo.value.character.id,
                            taskId = chatViewModelEvent.taskId
                        ),
                        onEach = { event ->
                            when (event.status) {
                                EventSourceStatus.OPEN -> {
                                    _state.chatList.update { it + ChatMessage(id = botChatId, author = Author.CHAT_BOT, message = "", time = System.currentTimeMillis(), isLoading = true) }
                                }

                                EventSourceStatus.CLOSED -> {
                                    connectEventSourceUseCase.cancelJob()
                                }

                                EventSourceStatus.ERROR -> {
                                    val list = _state.chatList.value
                                    list.findLast { it.id == botChatId }?.let {
                                        _state.chatList.update { list -> list.map { if (it.id == botChatId) it.copy(message = characterIdOfFailMessage(), isLoading = false) else it } }
                                    }
                                    connectEventSourceUseCase.cancelJob()
                                }

                                EventSourceStatus.SUCCESS -> {
                                    event.msg?.let { msg ->
                                        safeLet(msg.type, msg.contents) { type, contents ->
                                            if (type == "model_output") {
                                                val list = _state.chatList.value
                                                list.findLast { it.id == botChatId }?.let {
                                                    _state.chatList.update { list -> list.map { if (it.id == botChatId) it.copy(message = contents, isLoading = false) else it } }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Log.e("LMH", "RESPONSE $event")
                        },
                        onError = {
                            _state.chatList.update { list ->
                                list + ChatMessage(
                                    id = botChatId,
                                    author = Author.CHAT_BOT,
                                    message = characterIdOfFailMessage(),
                                    time = System.currentTimeMillis(),
                                    isLoading = false
                                )
                            }
                            Log.e("LMH", "STOP?? $it")
                        },
                    )
                }
            }
        }
    }

    private fun characterIdOfFailMessage(): String {
        return when (_state.userInfo.value.character.id) {
            1 -> "앗 미안해.. 서버에 문제가 발생했거나, 이 미션 주제랑 너무 다른 질문을 한 것 같아. 다시 한 번만 더 물어봐줄 수 있을까?"
            2 -> "헉!!! 미안해!! 서버에 문제가 발생했거나 이 미션 주제랑 너무 다른 질문을 한 것 같아!!! 다시 한 번만 더 물어봐줄래?!!"
            else -> "허엄.... 서버 장치에 문제가 발생했거나 이 미션 주제랑 너무 다른 질문을 한 듯 하오.... 미안하네만 한 번 더 물어봐 줄 수 있나...?"
        }
    }
}