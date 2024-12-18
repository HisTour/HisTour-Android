package com.startup.histour.presentation.bundle.ui

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.startup.histour.R
import com.startup.histour.presentation.bundle.model.Attraction
import com.startup.histour.presentation.bundle.model.HistoryHoliday
import com.startup.histour.presentation.bundle.viewmodel.BundleViewModel
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BundleScreen(navController: NavController, bundleViewModel: BundleViewModel = hiltViewModel()) {

    fun navigateRecommendedSpotScreen(attraction: Attraction) {
        val attractionJson = Uri.encode(Gson().toJson(attraction))
        navController.navigate(MainScreens.RecommendedSpot.route + "/${attractionJson}")
    }

    val attractionList by bundleViewModel.state.attractionList.collectAsState()
    val historyHolidayList by bundleViewModel.state.historyHolidayList.collectAsState()
    val userInfo by bundleViewModel.state.userInfo.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = HistourTheme.colors.white000)) {
        HisTourTopBar(model = HistourTopBarModel(titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_bundle)))
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.bundle_recommended_spot_title),
                        style = HistourTheme.typography.head2.copy(color = HistourTheme.colors.gray900)
                    )
                    Image(modifier = Modifier.noRippleClickable {
                        bundleViewModel.fetchAttraction()
                    }, painter = painterResource(id = R.drawable.btn_reload), contentDescription = null)
                }
                Spacer(modifier = Modifier.height(12.dp))
                // LazyGrid
                if (attractionList.isEmpty()) {
                    RecommendedSpotListView(List(4) { Attraction.orEmpty() }) {}
                } else {
                    RecommendedSpotListView(attractionList, ::navigateRecommendedSpotScreen)
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                    text = stringResource(id = R.string.bundle_today_history),
                    style = HistourTheme.typography.head2.copy(color = HistourTheme.colors.gray900)
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (historyHolidayList.isEmpty()) {
                    TodayHistoryEmptyView(userInfo.character)
                }
            }
            itemsIndexed(historyHolidayList) { index, item ->
                ToDayHistoryItem(item)
                if (index != historyHolidayList.size - 1) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun TodayHistoryEmptyView(characterInfo: CharacterModel) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = Modifier.size(180.dp),
            model = characterInfo.faceImageUrl,
            contentDescription = null,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                setToSaturation(0f)  // Saturation을 0으로 설정하여 흑백 효과
            })
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "오늘은 나만의 역사를 만들어 보아요", style = HistourTheme.typography.body3Medi.copy(color = HistourTheme.colors.gray400))
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
    list: List<Attraction>,
    onClickSpot: (Attraction) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(228.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = false
    ) {
        items(
            items = list,
        ) {
            RecommendedSpotListItem(it, onClickSpot)
        }
    }
}

@Composable
private fun RecommendedSpotListItem(
    attraction: Attraction,
    onClickSpot: (Attraction) -> Unit
) {
    val isError = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .height(109.dp)
    ) {
        AsyncImage(
            model = attraction.imageUrl,
            contentScale = ContentScale.Crop,
            onError = {
                Log.e("LMH", "DISPLAY IMAGE ERROR ${it.result.throwable}")
                isError.value = true
            },
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .noRippleClickable {
                    onClickSpot.invoke(attraction)
                },
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(0.5F)),
                        start = Offset.Zero,
                        end = Offset(0F, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                .align(Alignment.BottomStart),
            textAlign = TextAlign.Start,
            style = HistourTheme.typography.body3Medi.copy(color = HistourTheme.colors.white000),
            text = attraction.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** @param date 나중에 날짜 포맷으로 바꿔야함
 * */
@Composable
private fun ToDayHistoryItem(historyHoliday: HistoryHoliday) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(HistourTheme.colors.yellow100, shape = RoundedCornerShape(4.dp))
            .padding(start = 16.dp)
            .height(40.dp)
            .noRippleClickable {
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = convertDateUsingTime(historyHoliday.date),
                style = HistourTheme.typography.detail1Regular.copy(
                    color = HistourTheme.colors.yellow700
                ),
                maxLines = 1,
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = historyHoliday.name,
                style = HistourTheme.typography.body3Medi.copy(
                    color = HistourTheme.colors.gray800
                ),
                overflow = TextOverflow.Visible,
                maxLines = 1,
            )
        }
    }
}

private fun convertDateUsingTime(inputDate: String): String = runCatching {
    // 입력 형식 (yyyyMMdd)
    val inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    // 출력 형식 (MM.dd)
    val outputFormatter = DateTimeFormatter.ofPattern("MM.dd")

    // 입력 날짜를 LocalDate로 변환
    val date = LocalDate.parse(inputDate, inputFormatter)
    // 원하는 형식으로 변환
    date.format(outputFormatter)
}.getOrElse { inputDate }
