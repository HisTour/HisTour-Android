package com.startup.histour.presentation.mission.ui

import CTAImageButton
import CTAImageButtonModel
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.startup.histour.presentation.mission.util.MissionValues
import com.startup.histour.presentation.mission.util.MissionValues.KEYWORD_TASK
import com.startup.histour.presentation.mission.util.MissionValues.READING_TASK
import com.startup.histour.presentation.mission.viewmodel.TaskMissionEvent
import com.startup.histour.presentation.mission.viewmodel.TaskMissionViewModel
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.button.CTAButton
import com.startup.histour.presentation.widget.button.CTAMode
import com.startup.histour.presentation.widget.dialog.MissionDialogType
import com.startup.histour.presentation.widget.dialog.MissionHintDialog
import com.startup.histour.presentation.widget.textfield.ChatTextField
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskMissionScreen(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    taskMissionViewModel: TaskMissionViewModel = hiltViewModel(),
    submissionId: Int = 1,
    initialQuizCount: Int = 0
) {

    BackHandler {
        navController.popBackStack()
    }

    LaunchedEffect(submissionId) {
        taskMissionViewModel.getTasks(submissionId)
    }

    LaunchedEffect(taskMissionViewModel) {
        taskMissionViewModel.initClearedQuizCount(initialQuizCount)

        taskMissionViewModel.event.collectLatest { event ->
            when (event) {
                is TaskMissionEvent.MoveToClearScreen -> {
                    navController.navigate(MainScreens.MissionClear.route + "/${event.clearType}" + "/${event.subMissionType}" + "/${submissionId}" + "/${event.completedCount + 1}" + "/${event.requiredCount}") {
                        popUpTo(
                            navController.currentBackStackEntry?.destination?.id ?: return@navigate
                        ) {
                            inclusive = true
                        }
                    }
                }

                is TaskMissionEvent.ShowToast -> {
                    snackBarHostState.showSnackbar(event.msg)
                }

                is TaskMissionEvent.MoveToNextPage -> {
                    taskMissionViewModel.moveToNextTask()
                }
            }
        }
    }

    var showHintDialog by remember { mutableStateOf(false) }
    var showAnswerDialog by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }

    val tasksData = taskMissionViewModel.state.quizzesList.collectAsState()
    val submissionName = taskMissionViewModel.state.subMissionName.collectAsState()

    var taskType by remember {
        mutableStateOf(READING_TASK)
    }

    var answer by remember {
        mutableStateOf("")
    }
    var isAnswerSubmitted by remember { mutableStateOf(false) }


    // 퀴즈가 얼마나 클리어 되었는가 중복 채점시에는 올라가면 안되고 해당 숫자 기준으로 뷰페이저 페이지 최대치가 설정됨
    val clearedQuizCount = taskMissionViewModel.clearedQuizCount.collectAsState()
    // 현재 번호 기준, 페이지넘버, 채점 api id, 페이지 데이터 로드에 쓰임
    val currentTaskNumber by taskMissionViewModel.currentTaskNumber.collectAsState()

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
                titleStyle = HistourTopBarModel.TitleStyle.SubMissionText(
                    submissionName.value.split(
                        ":"
                    ).last().trim()
                ),
            ),
        )

        when (taskType) {
            READING_TASK -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(end = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 29.dp)
                            .border(
                                1.dp,
                                color = HistourTheme.colors.gray400,
                                shape = CircleShape
                            )
                            .padding(vertical = 4.dp, horizontal = 10.dp)
                            .wrapContentSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_number, currentTaskNumber),
                            color = HistourTheme.colors.green400,
                            style = HistourTheme.typography.detail2Semi
                        )
                    }
                }
            }

            KEYWORD_TASK -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
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
                                //TODO 챗봇
                                navController.navigate(MainScreens.GPT.route)
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
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 29.dp, end = 24.dp)
                            .border(
                                1.dp,
                                color = HistourTheme.colors.gray400,
                                shape = CircleShape
                            )
                            .padding(vertical = 4.dp, horizontal = 10.dp)
                            .wrapContentSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_number, currentTaskNumber),
                            color = HistourTheme.colors.green400,
                            style = HistourTheme.typography.detail2Semi
                        )
                    }
                }

                if (showHintDialog) {
                    MissionHintDialog(
                        dialogContent = tasksData.value[currentTaskNumber - 1].hint,
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
                        dialogContent = tasksData.value[currentTaskNumber - 1].answer,
                        onClickAnswer = { },
                        onClickClose = { showAnswerDialog = false },
                        missionContentData = null,
                        missionDialogType = MissionDialogType.ANSWER
                    )
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
            val pagerState = rememberPagerState(
                initialPage = clearedQuizCount.value,
                pageCount = {
                    minOf(clearedQuizCount.value + 1, tasksData.value.size)
                }
            )

            LaunchedEffect(clearedQuizCount.value, tasksData.value.size) {
                val targetPage = minOf(clearedQuizCount.value, tasksData.value.size - 1)
                if (pagerState.currentPage != targetPage) {
                    pagerState.animateScrollToPage(targetPage)
                }
            }

            LaunchedEffect(pagerState.currentPage) {
                val newTaskNumber = pagerState.currentPage + 1
                if (newTaskNumber != currentTaskNumber) {
                    taskMissionViewModel.updateCurrentTaskNumber(newTaskNumber)
                }

                taskType = tasksData.value.getOrNull(pagerState.currentPage)?.type
                    ?: MissionValues.READING_TASK

                isAnswerSubmitted = pagerState.currentPage < clearedQuizCount.value

                if (isAnswerSubmitted && taskType == KEYWORD_TASK) {
                    answer = tasksData.value[pagerState.currentPage].answer
                    enabled = true
                } else {
                    answer = ""
                    enabled = false
                }
            }

            LaunchedEffect(currentTaskNumber) {
                if (currentTaskNumber > 0 && currentTaskNumber <= tasksData.value.size) {
                    val targetPage = currentTaskNumber - 1
                    if (pagerState.currentPage != targetPage) {
                        pagerState.animateScrollToPage(targetPage)
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = HistourTheme.colors.white000),
                userScrollEnabled = clearedQuizCount.value < tasksData.value.size
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp)),
                            model = tasksData.value[page].imageUrl,
                            contentDescription = "task_mission",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        when (taskType) {
            READING_TASK -> {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    CTAButton(R.string.next, CTAMode.Enable.instance()) {
                        taskMissionViewModel.checkAnswer(
                            isLast = currentTaskNumber == tasksData.value.size,
                            taskId = tasksData.value[currentTaskNumber - 1].id
                        )
                    }
                }
            }

            KEYWORD_TASK -> {
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
                            text = answer,
                            enabled = !isAnswerSubmitted,
                            textStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray900),
                            placeHolderStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray400),
                            placeHolder = R.string.mission_textfield_hint,
                            onValueChange = {
                                enabled = it.isNotEmpty()
                                answer = it
                            },
                            onComplete = {}
                        )
                        if (enabled) {
                            Image(
                                modifier = Modifier
                                    .size(42.dp)
                                    .noRippleClickable {
                                        if (isAnswerSubmitted) {
                                            taskMissionViewModel.showAlreadyAnsweredToast()
                                        } else {
                                            taskMissionViewModel.checkAnswer(
                                                isLast = currentTaskNumber == tasksData.value.size,
                                                taskId = tasksData.value[currentTaskNumber - 1].id,
                                                answer
                                            )
                                        }
                                    },
                                painter = painterResource(id = R.drawable.btn_send_enabled),
                                contentDescription = "enabled"
                            )
                        } else {
                            Image(
                                modifier = Modifier.size(42.dp),
                                painter = painterResource(id = R.drawable.btn_send_disabled),
                                contentDescription = "disabled"
                            )
                        }
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
        TaskMissionScreen(
            navController = rememberNavController(),
            snackBarHostState = SnackbarHostState(),
            taskMissionViewModel = hiltViewModel()
        )
    }
}