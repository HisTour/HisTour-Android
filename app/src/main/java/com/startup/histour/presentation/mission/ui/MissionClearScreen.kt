package com.startup.histour.presentation.mission.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
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
import com.startup.histour.presentation.mission.util.MissionValues.FINAL_MISSION
import com.startup.histour.presentation.mission.util.MissionValues.INTRO_TYPE
import com.startup.histour.presentation.mission.util.MissionValues.LAST_SUBMISSION
import com.startup.histour.presentation.mission.util.MissionValues.SUBMISSION
import com.startup.histour.presentation.mission.viewmodel.MissionClearViewModel
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.widget.button.CTAButton
import com.startup.histour.presentation.widget.button.CTAMode
import com.startup.histour.presentation.widget.progressbar.HistourProgressBar
import com.startup.histour.presentation.widget.progressbar.HistourProgressBarModel
import com.startup.histour.presentation.widget.progressbar.ProgressbarType
import com.startup.histour.ui.theme.HistourTheme


@Composable
fun MissionClearScreen(
    navController: NavController,
    missionClearViewModel: MissionClearViewModel = hiltViewModel(),
    clearType: String = SUBMISSION,
    subMissionType: String = INTRO_TYPE,
    completedMissionId: Int = 1,
    clearedMissionCount: Int = 0,
    totalMissionCount: Int = 1,
    place: String? = "수원 화성",
) {

    LaunchedEffect(clearType) {
        when (clearType) {
            LAST_SUBMISSION -> missionClearViewModel.choiceFinalSubMission(completedMissionId)
            FINAL_MISSION -> missionClearViewModel.clearFinalMission()
        }
    }

    val userInfo by missionClearViewModel.state.userInfo.collectAsState()
    val progress = runCatching {
        (clearedMissionCount.toFloat() / totalMissionCount.toFloat()).takeIf { it >= 0F } ?: 0F
    }.getOrElse { 0F }
    val titleText = when (clearType) {
        SUBMISSION, LAST_SUBMISSION -> stringResource(
            id = R.string.mission_clear_step,
            clearedMissionCount
        )

        else -> stringResource(R.string.mission_clear_place, place ?: "수원 화성")
    }
    val descriptionText = when (clearType) {
        SUBMISSION, LAST_SUBMISSION -> R.string.mission_clear_submission
        else -> R.string.mission_clear_mission
    }

    val catButtonText = when (clearType) {
        SUBMISSION -> R.string.mission_clear_choice_submission
        LAST_SUBMISSION -> R.string.mission_clear_move_to_final
        else -> R.string.dialog_close
    }

    val spaceHeight = when (clearType) {
        LAST_SUBMISSION, FINAL_MISSION -> 54
        else -> 24
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(HistourTheme.colors.white000)
            .paint(
                painter = painterResource(id = R.drawable.bg_fanfare),
                contentScale = ContentScale.Fit
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(111.dp))
                    .height(27.dp)
                    .width(IntrinsicSize.Max)
                    .background(HistourTheme.colors.green200),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = titleText,
                    style = HistourTheme.typography.body1Bold,
                    color = HistourTheme.colors.green400
                )
            }
            Text(
                text = stringResource(id = descriptionText),
                style = HistourTheme.typography.head2,
                color = HistourTheme.colors.gray900
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        AsyncImage(
            modifier = Modifier
                .size(277.dp),
            model = userInfo.character.normalImageUrl,
            contentDescription = "character"
        )

        Column {
            HistourProgressBar(
                histourProgressBarModel =
                HistourProgressBarModel(totalStep = totalMissionCount),
                progress = progress,
                currentStep = clearedMissionCount,
                progressbarType = ProgressbarType.TOOLTIP
            )
            Spacer(modifier = Modifier.height(spaceHeight.dp))
            CTAButton(
                text = catButtonText, mode = CTAMode.Enable.instance()
            ) {
                if (clearType == SUBMISSION) {
                    navController.navigate(MainScreens.SubMissionChoice.route + "/${subMissionType}" + "/${completedMissionId}") {
                        popUpTo(
                            navController.currentBackStackEntry?.destination?.id ?: return@navigate
                        ) {
                            inclusive = true
                        }
                    }
                } else {
                    //TODO match userinfo placeid
                    navController.navigate(MainScreens.MissionMap.route) {
                        popUpTo(
                            navController.currentBackStackEntry?.destination?.id ?: return@navigate
                        ) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (clearType == SUBMISSION) {
            CTAButton(
                text = R.string.mission_clear_cta, mode = CTAMode.SubEnable.instance()
            ) {
                //TODO match userinfo placeid
                navController.navigate(MainScreens.MissionMap.route) {
                    popUpTo(
                        navController.currentBackStackEntry?.destination?.id ?: return@navigate
                    ) {
                        inclusive = true
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
@Preview(Devices.PHONE)
fun PreviewMissionClearScreen() {
    HistourTheme {
        MissionClearScreen(
            navController = rememberNavController(),
            clearType = LAST_SUBMISSION,
            place = "수원 화성",
            clearedMissionCount = 1,
            totalMissionCount = 5
        )
//        MissionClearScreen(
//            navController = rememberNavController(),
//            clearType = CLEAR.SUBMISSION,
//            step = 5,
//            place = "강릉",
//            progress = 0.3f
//        )
    }
}