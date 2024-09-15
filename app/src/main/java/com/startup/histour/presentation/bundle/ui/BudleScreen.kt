package com.startup.histour.presentation.bundle.ui

import android.net.Uri
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun BundleScreen(navController: NavController, bundleViewModel: BundleViewModel = hiltViewModel()) {

    fun navigateRecommendedSpotScreen(attraction: Attraction) {
        val attractionJson = Uri.encode(Gson().toJson(attraction))
        navController.navigate(MainScreens.RecommendedSpot.route + "/${attractionJson}")
        Log.e("LMH", "NAVIGATE")
    }

    val attractionList by bundleViewModel.state.attractionList.collectAsState()
    val historyHolidayList by bundleViewModel.state.historyHolidayList.collectAsState()

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
            Image(modifier = Modifier.noRippleClickable {
                bundleViewModel.fetchAttraction()
            }, painter = painterResource(id = R.drawable.btn_reload), contentDescription = null)
        }
        Spacer(modifier = Modifier.height(12.dp))
        RecommendedSpotListView(attractionList, ::navigateRecommendedSpotScreen)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.bundle_today_history),
            style = HistourTheme.typography.head2.copy(color = HistourTheme.colors.gray900)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ToDayHistoryList(historyHolidayList)
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
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(0.5F)),
                    start = Offset.Zero,
                    end = Offset(0F, Float.POSITIVE_INFINITY)
                ),
                shape = RoundedCornerShape(8.dp)
            )
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
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                .align(Alignment.BottomCenter),
            style = HistourTheme.typography.body3Medi.copy(color = HistourTheme.colors.white000),
            text = attraction.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** @param list 나중에 Immutable 하도록 변경
 * */
@Composable
private fun ToDayHistoryList(list: List<HistoryHoliday>) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(
                items = list,
                key = { index, _ -> index },
            ) { _, item ->
                ToDayHistoryItem(item)
            }
        }
    }
}

/** @param date 나중에 날짜 포맷으로 바꿔야함
 * */
@Composable
private fun ToDayHistoryItem(historyHoliday: HistoryHoliday) {
    Box(
        modifier = Modifier
            .background(HistourTheme.colors.yellow100, shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
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
                text = historyHoliday.date,
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

        /*Image(
            painter = painterResource(id = R.drawable.btn_enter_history),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )*/
    }
}