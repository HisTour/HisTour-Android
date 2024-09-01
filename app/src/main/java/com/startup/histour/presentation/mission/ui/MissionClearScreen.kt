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
    MISSION
}

@Composable
fun MissionClearScreen(
    navController: NavController,
    clearType: CLEAR = CLEAR.SUBMISSION,
    step: Int? = null,
    place: String? = null,
    progress: Float = 0.0f
) {

    val titleText = when (clearType) {
        CLEAR.SUBMISSION -> stringResource(id = R.string.mission_clear_step, step ?: 1)
        CLEAR.MISSION -> stringResource(R.string.mission_clear_place, place ?: "강릉")
    }
    val descriptionText = when (clearType) {
        CLEAR.SUBMISSION -> R.string.mission_clear_submission
        CLEAR.MISSION -> R.string.mission_clear_mission
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
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
                progress = if (clearType == CLEAR.MISSION) 1.0f else progress ?: 0.0f,
                currentStep = 0,
                progressbarType = ProgressbarType.TOOLTIP
            )
            Spacer(modifier = Modifier.height(24.dp))
            CTAButton(
                text = R.string.mission_clear_choice_submission, mode = CTAMode.Enable.instance()
            ) {
                //TODO submission tas
            }
            Spacer(modifier = Modifier.height(8.dp))
            CTAButton(
                text = R.string.mission_clear_cta, mode = CTAMode.SubEnable.instance()
            ) {
                navController.navigate(MainScreens.MissionMap.route)
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
            clearType = CLEAR.MISSION,
            step = 5,
            place = "강릉",
            progress = 0.3f
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