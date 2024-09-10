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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.startup.histour.R
import com.startup.histour.presentation.util.extensions.rippleClickable
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme
import okhttp3.internal.immutableListOf

@Composable
fun SubMissionChoiceScreen(navController: NavController) {
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
                    onClickLeftIcon = {},
                ),
                rightSectionType = HistourTopBarModel.RightSectionType.Empty,
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.mission_clear_choice_submission),
            ),
        )
        // AsyncImage
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(215.dp)
                .padding(all = 24.dp),
            painter = painterResource(id = R.drawable.img_chat),
            contentDescription = "submission"
        )
        SubMissionList()
    }
}

@Composable
private fun SubMissionList(
    list: List<Pair<String, String>> = immutableListOf(
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair("1952.05.07", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?")
    )
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(
                items = list,
                key = { index, _ -> index },
            ) { _, item ->
                val (date, historyStr) = item
                SubMissionItem(date, historyStr)
            }
        }
    }
}

@Composable
private fun SubMissionItem(missionType: String, spot: String) {
    Box(
        modifier = Modifier
            .background(HistourTheme.colors.yellow100, shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(start = 16.dp)
            .height(40.dp)
            .rippleClickable {
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = missionType,
                style = HistourTheme.typography.detail1Regular.copy(
                    color = HistourTheme.colors.yellow700
                ),
                maxLines = 1,
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = spot,
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