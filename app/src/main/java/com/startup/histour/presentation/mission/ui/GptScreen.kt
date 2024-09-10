package com.startup.histour.presentation.mission.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.presentation.mission.viewmodel.Author
import com.startup.histour.presentation.mission.viewmodel.ChatMessage
import com.startup.histour.presentation.mission.viewmodel.ChatViewModel
import com.startup.histour.presentation.mission.viewmodel.ChatViewModelEvent
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.launch

@Composable
fun GptScreen(navController: NavController, chatViewModel: ChatViewModel = hiltViewModel()) {
    val chatList = chatViewModel.state.chatList.collectAsState()
    val characterModel = chatViewModel.state.characterModel.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    // 메시지가 추가될 때 마지막 아이템으로 스크롤
    LaunchedEffect(chatList.value.size) {
        if (chatList.value.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(chatList.value.size - 1) // 마지막 아이템으로 스크롤
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .windowInsetsPadding(WindowInsets.ime),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier.weight(1F),
            state = listState
        ) {
            items(chatList.value) { item ->
                ChatItem(item, characterModel.value)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = { Text(stringResource(id = R.string.chatting_placeholder)) },
                modifier = Modifier
                    .weight(1F)
                    .padding(8.dp)
            )
            Button(onClick = {
                if (chatViewModel.canSend()) {
                    keyboardController?.hide()
                    chatViewModel.notifyViewModelEvent(ChatViewModelEvent.SendMessage(text))
                    text = ""
                }
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatItem(message: ChatMessage, characterModel: CharacterModel) {
    Spacer(modifier = Modifier.height(24.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        when (message.author) {
            Author.USER -> {
                SendItem(message = message)
            }

            Author.CHAT_BOT -> {
                ReceiveItem(message = message, characterModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiveItem(message: ChatMessage = ChatMessage(id = 0, Author.CHAT_BOT, message = "안녕!", time = System.currentTimeMillis()), characterModel: CharacterModel = CharacterModel.orEmpty()) {
    HistourTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(HistourTheme.colors.green400, CircleShape)) {
                    AsyncImage(
                        model = characterModel.faceImageUrl,
                        contentScale = ContentScale.Crop,
                        onError = {
                            Log.e("LMH", "DISPLAY IMAGE ERROR ${it.result.throwable}")
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .noRippleClickable {
                            },
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = characterModel.name,
                    style = HistourTheme.typography.body3Bold.copy(
                        color = HistourTheme.colors.gray900
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(HistourTheme.colors.gray100)
                    .padding(16.dp)
            ) {
                if (message.isLoading) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(HistourTheme.colors.D9DD9D9, CircleShape)
                            )
                        }
                    }
                } else {
                    Text(text = message.message, style = HistourTheme.typography.body3Reg.copy(color = HistourTheme.colors.gray900))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SendItem(message: ChatMessage = ChatMessage(id = 0, Author.USER, message = "안녕!", time = System.currentTimeMillis())) {
    HistourTheme {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(HistourTheme.colors.yellow200)
                    .padding(16.dp)
            ) {
                Text(text = message.message, style = HistourTheme.typography.body3Reg.copy(color = HistourTheme.colors.gray900))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewGptScreen() {
    GptScreen(navController = rememberNavController(), chatViewModel = hiltViewModel())
}