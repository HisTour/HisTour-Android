package com.startup.histour.presentation.mission.ui

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
import androidx.compose.runtime.collectAsState
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
import com.startup.histour.presentation.mission.viewmodel.MissionViewModel
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.widget.mission.ProgressingTaskDataModel
import com.startup.histour.presentation.widget.mission.SUBMISSIONSTATE
import com.startup.histour.presentation.widget.mission.SubMissionItem
import com.startup.histour.ui.theme.HistourTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MissionMapScreen(
    navController: NavController,
    missionViewModel: MissionViewModel = hiltViewModel()
) {
    val subtitle = Html.fromHtml(
        stringResource(R.string.submission_subtitle, "춘천"),
        Html.FROM_HTML_MODE_COMPACT
    )
    val list = missionViewModel.state.missionList.collectAsState()
    val requiredMissionCount = missionViewModel.state.requiredMissionCount.collectAsState()

    // 날짜-시간 파싱을 위한 포맷터
    val sortedList = remember(list.value, requiredMissionCount.value) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        // COMPLETE와 PROGRESS 상태의 미션만 필터링하고 정렬
        val completeAndProgress = list.value
            .filter { it.state == "COMPLETE" || it.state == "PROGRESS" }
            .sortedWith(
                compareByDescending<ResponseMission> { it.state == "COMPLETE" }
                    .thenBy {
                        it.updateAt?.let { date ->
                            try {
                                LocalDateTime.parse(date, formatter)
                            } catch (e: Exception) {
                                LocalDateTime.MAX
                            }
                        } ?: LocalDateTime.MAX
                    }
                    .thenByDescending { it.state == "PROGRESS" }
            )

        // requiredMissionCount 만큼만 선택
        val selectedMissions = completeAndProgress.take(requiredMissionCount.value)

        // 남은 공간에 BEFORE 상태 미션 추가
        val remainingCount = requiredMissionCount.value - selectedMissions.size
        val beforeMissions = List(remainingCount) {
            ResponseMission(
                clearedQuizCount = 0,
                id = null,
                name = "미션 ${it + 1}",
                state = "BEFORE",
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
                text = stringResource(id = R.string.submission_title, "깨비"),
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
                    "BEFORE" -> SUBMISSIONSTATE.BEFORE
                    "PROGRESS" -> SUBMISSIONSTATE.PROGRESS
                    "COMPLETE" -> SUBMISSIONSTATE.COMPLETE
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
                        ?: "수원화성",
                    state = state,
                    data = progressDataModel
                ) {
                    when (missionData.state) {
                        "PROGRESS" -> {
                            if (missionData.totalQuizCount!! <= missionData.clearedQuizCount!!) {
                                navController.navigate(MainScreens.SubMissionChoice.route)
                            } else {
                                navController.navigate(MainScreens.MissionTask.route)
                            }
                        }
                        else -> Unit
                    }
                }
            }

        }
    }
}
