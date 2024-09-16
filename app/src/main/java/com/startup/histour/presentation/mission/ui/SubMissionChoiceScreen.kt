package com.startup.histour.presentation.mission.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.data.dto.mission.ResponseMission
import com.startup.histour.presentation.mission.viewmodel.MissionClearViewModel
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.util.extensions.rippleClickable
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun SubMissionChoiceScreen(
    navController: NavController,
    missionClearViewModel: MissionClearViewModel = hiltViewModel()
) {

    val curMissionType = "INTRO"
    val subMissionId = 1
    val list = missionClearViewModel.state.missionList.collectAsState()
    val imageUrl = missionClearViewModel.state.imageUrl.collectAsState()

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
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.mission_clear_choice_submission),
            ),
        )
        AsyncImage(
            model = imageUrl.value,
            contentDescription = "submission_choice",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = 24.dp),
        )

        SubMissionList(
            missionClearViewModel,
            modifier = Modifier.weight(1f),
            list.value,
            curMissionType,
            subMissionId
        )
    }
}

@Composable
private fun SubMissionList(
    viewModel: MissionClearViewModel,
    modifier: Modifier,
    list: List<ResponseMission>,
    currentMissionType: String,
    currentMissionId: Int
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(
                items = list,
                key = { index, _ -> index },
            ) { _, item ->
                if (currentMissionType == "INTRO") {
                    if (item.type == "NORMAL") {
                        SubMissionItem(item.id ?: 1, item.name ?: "수원 화성") {
                            viewModel.clearAndChoiceSubMission(
                                completeMissionId = currentMissionId,
                                nextMissionId = item.id ?: 1
                            )
                        }
                    }
                } else if (currentMissionType == "NORMAL") {
                    if (item.state != "COMPLETE" && item.type == "NORMAL") {
                        SubMissionItem(item.id ?: 1, item.name ?: "수원 화성") {
                            viewModel.clearAndChoiceSubMission(
                                completeMissionId = currentMissionId,
                                nextMissionId = item.id ?: 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SubMissionItem(missionId: Int, missionName: String, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .background(HistourTheme.colors.yellow100, shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(start = 16.dp)
            .height(40.dp)
            .noRippleClickable {
                onClick.invoke(missionId)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = missionName,
                style = HistourTheme.typography.body3Medi.copy(
                    color = HistourTheme.colors.gray800
                ),
                overflow = TextOverflow.Visible,
                maxLines = 1,
            )
        }

        Image(
            painter = painterResource(id = R.drawable.btn_enter_history),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )
    }
}

@Preview
@Composable
fun PreviewSubMissionChoiceScreen() {
    HistourTheme {
        SubMissionChoiceScreen(rememberNavController())
    }
}