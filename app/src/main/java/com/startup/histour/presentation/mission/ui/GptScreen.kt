package com.startup.histour.presentation.mission.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.startup.histour.presentation.mission.viewmodel.Author
import com.startup.histour.presentation.mission.viewmodel.ChatMessage
import com.startup.histour.presentation.mission.viewmodel.ChatViewModel
import com.startup.histour.presentation.mission.viewmodel.ChatViewModelEvent
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun GptScreen(navController: NavController, chatViewModel: ChatViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val chatList = chatViewModel.state.chatList.collectAsState()
        Button(onClick = { chatViewModel.notifyViewModelEvent(ChatViewModelEvent.SendMessage("HI")) }) {
            Text("Back")
        }
        LazyColumn {
            items(chatList.value) { item ->
                ChatItem(item)
            }
        }
    }
}

@Composable
fun ChatItem(message: ChatMessage) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 48f,
                    topEnd = 48f,
                    bottomStart = if (message.author == Author.USER) 48f else 0f,
                    bottomEnd = if (message.author == Author.USER) 0f else 48f
                )
            )
            .background(if (message.author == Author.USER) HistourTheme.colors.blue300 else HistourTheme.colors.gray50)
            .padding(16.dp)
    ) {
        Text(text = message.message)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewGptScreen() {
    GptScreen(navController = rememberNavController(), chatViewModel = hiltViewModel())
}