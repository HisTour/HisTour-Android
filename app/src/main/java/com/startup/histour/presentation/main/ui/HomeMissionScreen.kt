package com.startup.histour.presentation.main.ui

import CTAImageButton
import CTAImageButtonModel
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.startup.histour.presentation.main.viewmodel.MainViewModel
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.widget.button.CTAButton
import com.startup.histour.presentation.widget.button.CTAMode
import com.startup.histour.presentation.widget.progressbar.HistourProgressBar
import com.startup.histour.presentation.widget.progressbar.HistourProgressBarModel
import com.startup.histour.presentation.widget.progressbar.ProgressbarType
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun HomeMissionScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {

    val userInfo by mainViewModel.state.userInfo.collectAsState()
    val place by mainViewModel.state.place.collectAsState()
    val progress = runCatching { (place.clearedMissionCount.toFloat() / place.totalMissionCount.toFloat()).takeIf { it >= 0F } ?: 0F }.getOrElse { 0F }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        HisTourTopBar(
            model = HistourTopBarModel(
                leftSectionType = HistourTopBarModel.LeftSectionType.Empty,
                rightSectionType = HistourTopBarModel.RightSectionType.Icons(
                    listOf(HistourTopBarModel.TopBarIcon.SETTINGS),
                    onClickRightIcon = {
                        //TODO 세팅창 이동
                        navController.navigate(MainScreens.Setting.route)
                    },
                ),
                titleStyle = HistourTopBarModel.TitleStyle.TextWithIcon(
                    "강원도 춘천시",
                    R.drawable.ic_dropdown
                ) {
                    navController.navigate(MainScreens.Map.route)
                },
            ),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.bg_home),
                    contentScale = ContentScale.FillBounds
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .paint(painter = painterResource(id = R.drawable.svg_speech_bubble)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = userInfo.character.comment,
                    style = HistourTheme.typography.body3Medi, color = HistourTheme.colors.yellow700
                )
            }

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(190.dp)
                        .height(230.dp),
                    model = userInfo.character.normalImageUrl,
                    contentDescription = "character"
                )
                CTAImageButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    model = CTAImageButtonModel(
                        textId = R.string.home_character_setting,
                        drawableId = R.drawable.ic_ggabi
                    )
                ) {
                    navController.navigate(MainScreens.CharacterSetting.route)
                }
            }
            Spacer(modifier = Modifier.height(13.dp))
            Card(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .align(Alignment.End),
                colors = CardDefaults.cardColors(containerColor = HistourTheme.colors.white000),
            ) {
                Column(
                    modifier = Modifier.padding(all = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(65.dp),
                            model = userInfo.profileImageUrl,
                            contentDescription = "profile"
                        )
                        Column(
                            modifier = Modifier
                                .height(65.dp)
                                .padding(top = 8.dp, bottom = 5.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(
                                    id = R.string.home_mission_user_progress,
                                    "깨비"
                                ),
                                style = HistourTheme.typography.head4,
                                color = HistourTheme.colors.black
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_check),
                                    contentDescription = "progressing"
                                )

                                when (progress) {
                                    0f -> Text(
                                        text = stringResource(
                                            id = R.string.home_mission_before,
                                        ),
                                        style = HistourTheme.typography.detail1Regular,
                                        color = HistourTheme.colors.gray500,
                                    )

                                    1f -> Text(
                                        text = stringResource(
                                            id = R.string.home_mission_complete,
                                        ),
                                        style = HistourTheme.typography.detail1Regular,
                                        color = HistourTheme.colors.gray500,
                                    )

                                    else -> Text(
                                        text = stringResource(
                                            id = R.string.home_mission_progress, (progress * 100).toInt()
                                        ),
                                        style = HistourTheme.typography.detail1Regular,
                                        color = HistourTheme.colors.gray500,
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                    HistourProgressBar(
                        histourProgressBarModel =
                        HistourProgressBarModel(totalStep = 5),
                        progress = progress,
                        currentStep = 0,
                        progressbarType = ProgressbarType.IMAGE
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CTAButton(
                        text = R.string.home_mission_doing,
                        mode = CTAMode.Enable.instance()
                    ) {
                        navController.navigate(MainScreens.MissionTask.route)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
@Preview(Devices.PHONE)
fun PreviewMissionHomeScreen() {
    HistourTheme {
        HomeMissionScreen(navController = rememberNavController())
    }
}