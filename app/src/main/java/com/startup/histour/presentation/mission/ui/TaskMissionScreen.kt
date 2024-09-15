package com.startup.histour.presentation.mission.ui

import CTAImageButton
import CTAImageButtonModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.presentation.mission.viewmodel.TaskMissionViewModel
import com.startup.histour.presentation.widget.button.CTAButton
import com.startup.histour.presentation.widget.button.CTAMode
import com.startup.histour.presentation.widget.dialog.MissionDialogType
import com.startup.histour.presentation.widget.dialog.MissionHintDialog
import com.startup.histour.presentation.widget.textfield.ChatTextField
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme


enum class MissionType {
    INTRO,
    SUBMISSION,
    FINAL
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskMissionScreen(
    navController: NavController,
    taskMissionViewModel: TaskMissionViewModel = hiltViewModel(),
    type: MissionType = MissionType.SUBMISSION,
    placeId: Int = 1
) {

    var enabled by remember { mutableStateOf(false) }
    var taskNumber by remember { mutableIntStateOf(1) }
    val tasksData = taskMissionViewModel.state.missionList.collectAsState()
    var taskType by remember {
        mutableStateOf("READING")
    }

    var showHintDialog by remember { mutableStateOf(false) }
    var showAnswerDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HisTourTopBar(
            model =
            HistourTopBarModel(
                leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                    listOf(HistourTopBarModel.TopBarIcon.BACK),
                    onClickLeftIcon = {
                        navController.popBackStack()
                    },
                ),
                rightSectionType = HistourTopBarModel.RightSectionType.Empty,
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character),
            ),
        )
        when (taskType) {
            "READING" -> {
                Column(
                    modifier = Modifier.height(56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                color = HistourTheme.colors.gray400,
                                shape = CircleShape
                            )
                            .padding(vertical = 4.dp, horizontal = 10.dp)
                            .wrapContentSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_number, taskNumber),
                            color = HistourTheme.colors.green400,
                            style = HistourTheme.typography.detail2Semi
                        )
                    }
                }
            }

            "KEYWORD" -> {
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(top = 24.dp, start = 24.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CTAImageButton(
                            modifier = Modifier,
                            model = CTAImageButtonModel(
                                textId = R.string.chat_bot,
                                drawableId = R.drawable.ic_ggabi
                            )
                        ) {
                            // 클릭 이벤트 처리
                        }
                        CTAImageButton(
                            modifier = Modifier,
                            model = CTAImageButtonModel(
                                textId = R.string.dialog_hint,
                                drawableId = R.drawable.ic_hint_gray
                            )
                        ) {
                            showHintDialog = true
                        }

                        if (showHintDialog) {
                            MissionHintDialog(
                                dialogContent = tasksData.value[taskNumber - 1].hint,
                                onClickAnswer = {
                                    showAnswerDialog = true
                                    showHintDialog = false
                                },
                                onClickClose = { showHintDialog = false },
                                missionContentData = null,
                                missionDialogType = MissionDialogType.HINT
                            )
                        }

                        if (showAnswerDialog) {
                            MissionHintDialog(
                                dialogContent = tasksData.value[taskNumber - 1].answer,
                                onClickAnswer = { },
                                onClickClose = { showAnswerDialog = false },
                                missionContentData = null,
                                missionDialogType = MissionDialogType.ANSWER
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                color = HistourTheme.colors.gray400,
                                shape = CircleShape
                            )
                            .padding(vertical = 4.dp, horizontal = 10.dp)
                            .wrapContentSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_number, taskNumber),
                            color = HistourTheme.colors.green400,
                            style = HistourTheme.typography.detail2Semi
                        )
                    }
                    Spacer(modifier = Modifier.width(24.dp))  // 오른쪽 여백 추가

                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 24.dp, end = 24.dp, bottom = 11.dp)
                .background(Color.Gray)

        ) {
            val pagerState = rememberPagerState(pageCount = { tasksData.value.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = HistourTheme.colors.white000)
            ) { page ->
                taskNumber = pagerState.currentPage + 1
                taskType = tasksData.value[taskNumber - 1].type
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = tasksData.value[taskNumber - 1].imageUrl,
                            contentDescription = "task_mission",
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
        when (taskType) {
            "READING" -> {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    CTAButton(R.string.next, CTAMode.Enable.instance()) {}
                }

            }

            "KEYWORD" -> {
                Column(modifier = Modifier.align(Alignment.End)) {
                    Row(
                        modifier = Modifier
                            .height(64.dp)
                            .fillMaxWidth()
                            .background(HistourTheme.colors.white000),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_pen),
                            contentDescription = "pen"
                        )
                        Text(
                            text = stringResource(id = R.string.mission_guide),
                            style = HistourTheme.typography.body2Reg,
                            color = HistourTheme.colors.gray400
                        )
                    }
                    Row(
                        modifier = Modifier
                            .height(114.dp)
                            .background(HistourTheme.colors.green200)
                            .padding(top = 30.dp, start = 24.dp, end = 24.dp, bottom = 34.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        ChatTextField(
                            modifier = Modifier.weight(1f),
                            text = "",
                            enabled = true,
                            textStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray900),
                            placeHolderStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray400),
                            placeHolder = R.string.mission_textfield_hint,
                            onValueChange = {
                                enabled = it.isNotEmpty()
                            }
                        ) {

                        }
                        if (enabled) Image(
                            modifier = Modifier
                                .size(42.dp),
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
        }
    }
}

@Preview(Devices.PHONE)
@Composable
fun PreviewTaskMissionScreen() {
    HistourTheme {
        TaskMissionScreen(navController = rememberNavController(), type = MissionType.SUBMISSION)
    }
}