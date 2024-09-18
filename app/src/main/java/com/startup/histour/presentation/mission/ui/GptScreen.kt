package com.startup.histour.presentation.mission.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.startup.histour.R
import com.startup.histour.presentation.mission.viewmodel.Author
import com.startup.histour.presentation.mission.viewmodel.ChatMessage
import com.startup.histour.presentation.mission.viewmodel.ChatViewModel
import com.startup.histour.presentation.mission.viewmodel.ChatViewModelEvent
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.dialog.HistourDialog
import com.startup.histour.presentation.widget.dialog.HistourDialogModel
import com.startup.histour.presentation.widget.dialog.TYPE
import com.startup.histour.presentation.widget.textfield.ChatTextField
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel.RightSectionType.Text.State
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GptScreen(navController: NavController, chatViewModel: ChatViewModel = hiltViewModel()) {
    val chatList by chatViewModel.state.chatList.collectAsState()
    val userInfo by chatViewModel.state.userInfo.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.text_loading)
    )
    var isKeyboardVisible by remember { mutableStateOf(false) }

    val finishChatDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .windowInsetsPadding(WindowInsets.ime),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LaunchedEffect(isKeyboardVisible) {
            Log.e("LMH", "FOCUS KEYBOARD $isKeyboardVisible")
            coroutineScope.launch {
                delay(500)
                if (isKeyboardVisible && chatList.isNotEmpty()) {
                    listState.animateScrollToItem(chatList.size - 1) // 마지막 아이템으로 스크롤
                }
            }
        }
        // 메시지가 추가될 때 마지막 아이템으로 스크롤
        LaunchedEffect(chatList.size) {
            if (chatList.isNotEmpty()) {
                coroutineScope.launch {
                    listState.animateScrollToItem(chatList.size - 1) // 마지막 아이템으로 스크롤
                }
            }
        }
        HisTourTopBar(
            model = HistourTopBarModel(
                leftSectionType = HistourTopBarModel.LeftSectionType.Empty,
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.dialog_gpt_title),
                rightSectionType = HistourTopBarModel.RightSectionType.Text(stringResId = R.string.finish, State.FINISH) {
                    finishChatDialog.value = true
                }
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1F)
                .padding(bottom = 24.dp),
            state = listState
        ) {
            items(chatList) { item ->
                ChatItem(item, userInfo.character, preLoaderLottieComposition)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            var text by remember { mutableStateOf("") }
            Row(
                modifier = Modifier
                    .background(HistourTheme.colors.green200)
                    .padding(top = 12.dp, start = 24.dp, end = 24.dp, bottom = 12.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                ChatTextField(
                    modifier = Modifier
                        .weight(1f),
                    text = text,
                    enabled = true,
                    textStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray900),
                    placeHolderStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray400),
                    placeHolder = R.string.chatting_placeholder,
                    onValueChange = {
                        text = it
                    }
                ) {

                }
                if (text.isNotBlank()) Image(
                    modifier = Modifier
                        .size(42.dp)
                        .noRippleClickable {
                            if (chatViewModel.canSend()) {
                                keyboardController?.hide()
                                chatViewModel.notifyViewModelEvent(ChatViewModelEvent.SendMessage(text))
                                text = ""
                            }
                        },
                    painter = painterResource(id = R.drawable.btn_send_enabled),
                    contentDescription = "enabled"
                )
                else Image(
                    modifier = Modifier
                        .size(42.dp),
                    painter = painterResource(id = R.drawable.btn_send_disabled),
                    contentDescription = "disenabled"
                )
            }
        }
    }
    if (finishChatDialog.value) {
        HistourDialog(
            histourDialogModel = HistourDialogModel(
                titleRes = R.string.dialog_title_finish_gpt,
                positiveButtonRes = R.string.finish,
                negativeButtonRes = R.string.dialog_cancel,
                type = TYPE.DEFAULT
            ),
            onClickPositive = { content ->
                finishChatDialog.value = false
                navController.popBackStack()
            },
            onClickNegative = {
                finishChatDialog.value = false
            },
        )
    }
}

@Composable
fun ChatItem(message: ChatMessage, characterModel: CharacterModel, lottieComposition: LottieComposition?) {
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
                ReceiveItem(message = message, characterModel, lottieComposition)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiveItem(
    message: ChatMessage = ChatMessage(id = 0, Author.CHAT_BOT, message = "안녕!", time = System.currentTimeMillis()),
    characterModel: CharacterModel = CharacterModel.orEmpty(),
    lottieComposition: LottieComposition? = null
) {
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
            ) {
                if (message.isLoading) {
                    LottieAnimation(
                        composition = lottieComposition,
                        iterations = LottieConstants.IterateForever,
                        restartOnPlay = true,
                        modifier = Modifier
                            .width(57.dp)
                            .height(37.dp)
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = message.message,
                        style = HistourTheme.typography.body3Reg.copy(
                            color = HistourTheme.colors.gray900
                        )
                    )
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