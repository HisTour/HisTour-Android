package com.startup.histour.presentation.bundle.ui

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.startup.histour.R
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun TodayHistoryStoryScreen(navController: NavController = rememberNavController()) {

    //TODO GET today history

    fun navigateBundleScreen() {
        navController.popBackStack()
    }

    val scrollState = rememberScrollState()

    Column {
        HisTourTopBar(
            model = HistourTopBarModel(
                leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                    leftIcons = listOf(HistourTopBarModel.TopBarIcon.BACK),
                    onClickLeftIcon = {
                        //navigateBundleScreen()
                    },
                ), titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_bundle_todaystory)
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = HistourTheme.colors.white000)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(33.dp))
            Image(
                modifier = Modifier
                    .width(150.dp)
                    .height(130.dp),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "pinkpong"
            )
            HistoryContent()
            RelatedSpots()
            Spacer(modifier = Modifier.size(16.dp))
        }
    }

}

@Composable
private fun HistoryContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = HistourTheme.colors.yellow100),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(top = 28.dp, bottom = 18.dp, start = 24.dp, end = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(111.dp))
                    .height(27.dp)
                    .width(IntrinsicSize.Max)
                    .background(HistourTheme.colors.yellow300),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_date),
                    tint = Color.Unspecified,
                    contentDescription = "date"
                )
                //TODO get data
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "1592년 5월 7일",
                    style = HistourTheme.typography.body3Reg,
                    color = HistourTheme.colors.yellow700
                )
            }
            //TODO get data
            Text(
                text = "임진왜란 합포 해전",
                style = HistourTheme.typography.head2,
                color = HistourTheme.colors.gray900
            )
            //TODO get data
            Text(
                text = "1592년(선조 25) 5월 4일 처녀출전한 이순신이 원균(元均)과 합세하여 5월 7일 옥포(玉浦)에서 왜선을 무찌른 뒤에 같은 날 영등포(永登浦) 앞바다로 이동, 적을 경계하면서 휴식준비를 하던 중 와키사카(脇坂安治)가 이끄는 왜선 5척이 지나간다는 척후장(斥候將)의 급보를 받고 곧 추격작전을 벌여 합포해전이 전개되었다. 이 때 세력이 약한 왜선들은 황급히 합포(지금의 창원시 마산합포구 산호2동 앞바다)로 도주하여 배를 버린 채 육지로 올라가 조총으로 대응하였고, 이순신의 지시에 따른 우척후장 김완(金浣), 중위장 이순신(李純信), 중부장 어영담(魚泳譚)을 비롯한 장령들이 총통과 화살로써 5척을 모두 불태워버렸으나 왜병들은 다 잡지 못하고 밤중에 남포(藍浦) 앞바다로 이동하였다. 이 해전은 불과 5척의 왜선을 상대한 것이지만 이순신의 철저한 경계로 쉽게 승리할 수 있었다.1592년(선조 25) 5월 4일 처녀출전한 이순신이 원균(元均)과 합세하여 5월 7일 옥포(玉浦)에서 왜선을 무찌른 뒤에 같은 날 영등포(永登浦) 앞바다로 이동, 적을 경계하면서 휴식준비를 하던 중 와키사카(脇坂安治)가 이끄는 왜선 5척이 지나간다는 척후장(斥候將)의 급보를 받고 곧 추격작전을 벌여 합포해전이 전개되었다. 이 때 세력이 약한 왜선들은 황급히 합포(지금의 창원시 마산합포구 산호2동 앞바다)로 도주하여 배를 버린 채 육지로 올라가 조총으로 대응하였고, 이순신의 지시에 따른 우척후장 김완(金浣), 중위장 이순신(李純信), 중부장 어영담(魚泳譚)을 비롯한 장령들이 총통과 화살로써 5척을 모두 불태워버렸으나 왜병들은 다 잡지 못하고 밤중에 남포(藍浦) 앞바다로 이동하였다. 이 해전은 불과 5척의 왜선을 상대한 것이지만 이순신의 철저한 경계로 쉽게 승리할 수 있었다.",
                style = HistourTheme.typography.body3Reg,
                color = HistourTheme.colors.gray700
            )
        }
    }
}

@Composable
private fun RelatedSpots() {
    //TODO get data
    val list = listOf("전라남도 남해", "충청남도 아산", "충청남도 아산", "충청남도 아산")

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = CardDefaults.cardColors(containerColor = HistourTheme.colors.gray100),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
                .padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.bundle_todaystory_related),
                style = HistourTheme.typography.body1Bold,
                color = HistourTheme.colors.gray900
            )
            //TODO get data
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                list.forEach { item ->
                    Text(
                        text = item,
                        style = HistourTheme.typography.detail1Regular,
                        color = HistourTheme.colors.gray700
                    )
                }
            }
        }
    }
}


@Composable
@Preview(device = Devices.PHONE)
private fun PreviewTodayHistory() {

    HistourTheme {
        TodayHistoryStoryScreen()
    }

}