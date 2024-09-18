package com.startup.histour.presentation.mission.ui

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.startup.histour.R
import com.startup.histour.data.dto.mission.ResponseMission
import com.startup.histour.presentation.main.viewmodel.MainViewModel
import com.startup.histour.presentation.mission.util.MissionValues
import com.startup.histour.presentation.mission.util.MissionValues.BEFORE_STATE
import com.startup.histour.presentation.mission.util.MissionValues.COMPLETE_STATE
import com.startup.histour.presentation.mission.util.MissionValues.FINAL_MISSION
import com.startup.histour.presentation.mission.util.MissionValues.FINAL_TYPE
import com.startup.histour.presentation.mission.util.MissionValues.PROGRESS_STATE
import com.startup.histour.presentation.mission.viewmodel.MissionViewModel
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.widget.mission.ProgressingTaskDataModel
import com.startup.histour.presentation.widget.mission.SUBMISSIONSTATE
import com.startup.histour.presentation.widget.mission.SubMissionItem
import com.startup.histour.ui.theme.HistourTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MissionMapScreen(
    navController: NavController,
    missionViewModel: MissionViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val subtitle = Html.fromHtml(
        stringResource(R.string.submission_subtitle, "수원 화성"),
        Html.FROM_HTML_MODE_COMPACT
    )

    val placeState by mainViewModel.state.place.collectAsState()

    LaunchedEffect(placeState) {
        missionViewModel.getSubMissionList(mainViewModel.state.place.value.placeId)
    }

    val list = missionViewModel.state.missionList.collectAsState()
    val requiredMissionCount = missionViewModel.state.requiredMissionCount.collectAsState()
    val userInfo by missionViewModel.state.userInfo.collectAsState()
    var remainingCount = 0

    // 날짜-시간 파싱을 위한 포맷터
    val sortedList = remember(list.value, requiredMissionCount.value) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        // COMPLETE와 PROGRESS 상태의 미션만 필터링하고 정렬
        val completeAndProgress = list.value
            .filter { it.state == COMPLETE_STATE || it.state == PROGRESS_STATE }
            .sortedWith(
                compareByDescending<ResponseMission> { it.state == COMPLETE_STATE }
                    .thenBy {
                        it.updateAt?.let { date ->
                            try {
                                LocalDateTime.parse(date, formatter)
                            } catch (e: Exception) {
                                LocalDateTime.MAX
                            }
                        } ?: LocalDateTime.MAX
                    }
                    .thenByDescending { it.state == PROGRESS_STATE }
            )

        // requiredMissionCount 만큼만 선택
        val selectedMissions = completeAndProgress.take(requiredMissionCount.value)

        // 남은 공간에 BEFORE 상태 미션 추가
        remainingCount = requiredMissionCount.value - selectedMissions.size

        val beforeMissions = List(remainingCount) {
            ResponseMission(
                clearedQuizCount = 0,
                id = 99,
                name = "아직 이르오",
                state = BEFORE_STATE,
                totalQuizCount = 0,
                type = null,
                updateAt = null
            )
        }
        selectedMissions + beforeMissions
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_missonmap),
                contentScale = ContentScale.FillBounds
            ),

        ) {

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(24.dp)
                .height(100.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.submission_title, userInfo.userName),
                style = HistourTheme.typography.head1,
                color = HistourTheme.colors.black
            )
            Text(
                text = subtitle.toString(),
                style = HistourTheme.typography.body2Reg,
                color = HistourTheme.colors.gray600
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            reverseLayout = true
        ) {
            items(sortedList.size) { index ->
                val missionData = sortedList[index]
                val state = when (missionData.state) {
                    BEFORE_STATE -> SUBMISSIONSTATE.BEFORE
                    PROGRESS_STATE -> SUBMISSIONSTATE.PROGRESS
                    COMPLETE_STATE -> SUBMISSIONSTATE.COMPLETE
                    else -> SUBMISSIONSTATE.BEFORE
                }
                val progressDataModel = when (state) {
                    SUBMISSIONSTATE.PROGRESS -> ProgressingTaskDataModel(
                        totalMissions = missionData.totalQuizCount ?: 0,
                        completedMissions = missionData.clearedQuizCount ?: 0
                    )

                    else -> null
                }
                SubMissionItem(
                    subMissionTitle = missionData.name?.split(":")?.last()?.trim()
                        ?: "수원 화성",
                    characterImageUrl = userInfo.character.normalImageUrl,
                    state = state,
                    data = progressDataModel
                ) {
                    when (missionData.state) {
                        PROGRESS_STATE -> {
                            if (missionData.totalQuizCount!! <= missionData.clearedQuizCount!!) {
                                if (missionData.type == FINAL_TYPE) {
                                    // 현재 미션이 파이널인 상황(다음 미션이 없다는 뜻)
                                    navController.navigate(MainScreens.MissionClear.route + "/${FINAL_MISSION}" + "/${missionData.type}" + "/${missionData.id}" + "/${requiredMissionCount.value}" + "/${requiredMissionCount.value}")
                                } else if (remainingCount == 1) {
                                    // 다음 미션이 파이널만 남은 상황
                                    navController.navigate(MainScreens.MissionClear.route + "/${MissionValues.LAST_SUBMISSION}" + "/${missionData.type}" + "/${missionData.id}" + "/${requiredMissionCount.value - 1}" + "/${requiredMissionCount.value}")
                                } else {
                                    navController.navigate(MainScreens.SubMissionChoice.route + "/${missionData.type}" + "/${missionData.id}")
                                }
                            } else {
                                navController.navigate(MainScreens.MissionTask.route + "/${missionData.id}" + "/${missionData.clearedQuizCount}")
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}
