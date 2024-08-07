package com.startup.histour.presentation.bundle.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.util.extensions.rippleClickable
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme
import okhttp3.internal.immutableListOf

@Composable
fun BundleScreen(navController: NavController = rememberNavController()) {

    fun navigateHistoryStoryScreen() {
        navController.navigate(MainScreens.RecommendedSpot.route)
    }

    fun navigateRecommendedSpotScreen() {
        navController.navigate(MainScreens.RecommendedSpot.route)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = HistourTheme.colors.white000),
    ) {
        HisTourTopBar(model = HistourTopBarModel(titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_bundle)))
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.bundle_recommended_spot_title),
                style = HistourTheme.typography.head2.copy(color = HistourTheme.colors.gray900)
            )
            Image(painter = painterResource(id = R.drawable.btn_reload), contentDescription = null)
        }
        Spacer(modifier = Modifier.height(12.dp))
        RecommendedSpotListView()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.bundle_today_history),
            style = HistourTheme.typography.head2.copy(color = HistourTheme.colors.gray900)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ToDayHistoryList()
    }
}

@Preview
@Composable
fun PreviewBundleScreen() {
    HistourTheme {
        BundleScreen(navController = rememberNavController())
    }
}

@Composable
private fun RecommendedSpotListView(
    list: List<Pair<String, String>> = immutableListOf<Pair<String, String>>(
        Pair<String, String>("가족과 함께 가기 좋은 역사 관광지", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair<String, String>("당일 코스로 즐겨보는 백제 문화 탐방", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair<String, String>("역사를 지킨 요새, 남한 산성", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?"),
        Pair<String, String>("가정의 달, 한복 체험 정보 20자 초과시...", "역사이야기가 이렇게 길 경우에 그라데이션이 있겠죠?")
    )
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = list,
        ) {
            val (spot, path) = it
            RecommendedSpotListItem(
                spot, path
            )
        }
    }
}


@Composable
private fun RecommendedSpotListItem(
    spotIntroduce: String,
    imagePath: String
) {
    val localConfiguration = LocalConfiguration.current
    val isError = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .height(109.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(0.5F)),
                    start = Offset.Zero,
                    end = Offset(0F, Float.POSITIVE_INFINITY)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { },
            )
    ) {
        AsyncImage(
            model = imagePath,
            contentScale = ContentScale.Crop,
            onError = {
                Log.e("LMH", "DISPLAY IMAGE ERROR ${it.result.throwable}")
                isError.value = true
            },
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .noRippleClickable {
                    if (!isError.value) {

                    }
                },
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                .align(Alignment.BottomCenter),
            style = HistourTheme.typography.body3Medi.copy(color = HistourTheme.colors.white000),
            text = spotIntroduce,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** @param list 나중에 Immutable 하도록 변경
 * */
@Composable
private fun ToDayHistoryList(
    list: List<Pair<String, String>> = immutableListOf(
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
                ToDayHistoryItem(date, historyStr)
            }
        }
    }
}

/** @param date 나중에 날짜 포맷으로 바꿔야함
 * */
@Composable
private fun ToDayHistoryItem(date: String, historyStr: String) {
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
                text = date,
                style = HistourTheme.typography.detail1Regular.copy(
                    color = HistourTheme.colors.yellow700
                ),
                maxLines = 1,
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = historyStr,
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