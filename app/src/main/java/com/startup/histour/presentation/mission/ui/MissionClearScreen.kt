package com.startup.histour.presentation.mission.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.startup.histour.R
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.widget.button.CTAButton
import com.startup.histour.presentation.widget.button.CTAMode
import com.startup.histour.presentation.widget.progressbar.HistourProgressBar
import com.startup.histour.presentation.widget.progressbar.HistourProgressBarModel
import com.startup.histour.presentation.widget.progressbar.ProgressbarType
import com.startup.histour.ui.theme.HistourTheme

enum class CLEAR {
    SUBMISSION,
    LASTSUBMISSION,
    MISSION
}

@Composable
fun MissionClearScreen(
    navController: NavController,
    clearType: CLEAR = CLEAR.SUBMISSION,
    place: String? = null,
    missionId:Int = 1,
    clearedMissionCount: Int = 0,
    totalMissionCount: Int = 1,
) {
    //TODO 진입 하자마자 PATCH /api/v1/missions/unlock 호출

    val titleText = when (clearType) {
        CLEAR.SUBMISSION, CLEAR.LASTSUBMISSION -> stringResource(
            id = R.string.mission_clear_step,
            clearedMissionCount
        )

        CLEAR.MISSION -> stringResource(R.string.mission_clear_place, place ?: "강릉")
    }
    val descriptionText = when (clearType) {
        CLEAR.SUBMISSION, CLEAR.LASTSUBMISSION -> R.string.mission_clear_submission
        CLEAR.MISSION -> R.string.mission_clear_mission
    }

    val catButtonText = when (clearType) {
        CLEAR.SUBMISSION -> R.string.mission_clear_choice_submission
        CLEAR.LASTSUBMISSION -> R.string.mission_clear_move_to_final
        CLEAR.MISSION -> R.string.dialog_close
    }

    val spaceHeight = when (clearType) {
        CLEAR.SUBMISSION -> 24
        CLEAR.LASTSUBMISSION, CLEAR.MISSION -> 54
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
        Image(
            modifier = Modifier
                .height(271.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_main), contentDescription = "character"
        )

        Column {
            HistourProgressBar(
                histourProgressBarModel =
                HistourProgressBarModel(totalStep = 5),
                progress = if (clearType == CLEAR.MISSION) 1.0f else (clearedMissionCount / totalMissionCount).toFloat(),
                currentStep = 0,
                progressbarType = ProgressbarType.TOOLTIP
            )
            Spacer(modifier = Modifier.height(spaceHeight.dp))
            CTAButton(
                text = catButtonText, mode = CTAMode.Enable.instance()
            ) {
                if (clearType == CLEAR.SUBMISSION) {
                    navController.navigate(MainScreens.SubMissionChoice.route)
                } else {
                    navController.navigate(MainScreens.MissionMap.route)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (clearType == CLEAR.SUBMISSION) {
                CTAButton(
                    text = R.string.mission_clear_cta, mode = CTAMode.SubEnable.instance()
                ) {
                    navController.navigate(MainScreens.MissionMap.route)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
@Preview(Devices.PHONE)
fun PreviewMissionClearScreen() {
    HistourTheme {
        MissionClearScreen(
            navController = rememberNavController(),
            clearType = CLEAR.LASTSUBMISSION,
            place = "강릉",
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