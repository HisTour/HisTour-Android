package com.startup.histour.presentation.mission.ui

import android.text.Html
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
import com.startup.histour.presentation.mission.viewmodel.MissionViewModel
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.widget.mission.ProgressingTaskDataModel
import com.startup.histour.presentation.widget.mission.SUBMISSIONSTATE
import com.startup.histour.presentation.widget.mission.SubMissionItem
import com.startup.histour.ui.theme.HistourTheme

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
            items(list.value.size) { index ->
                list.value[index].let { missionData ->
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
                    SubMissionItem(subMissionTitle = missionData.name?.split(":")?.last()?.trim() ?: "수원화성",
                        state = state,
                        data = progressDataModel,
                        {
                            //TODO 서브미션 변경하기
                        }, { navController.navigate(MainScreens.MissionTask.route) })
                }

            }
        }
    }
}