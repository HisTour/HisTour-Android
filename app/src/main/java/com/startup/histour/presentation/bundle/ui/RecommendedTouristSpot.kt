package com.startup.histour.presentation.bundle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.presentation.bundle.model.Attraction
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedSpotScreen(navController: NavController, attraction: Attraction) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()  // 스크롤 동작 설정
    Scaffold(
        topBar = {
            HisTourTopBar(
                model = HistourTopBarModel(
                    leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                        leftIcons = listOf(HistourTopBarModel.TopBarIcon.BACK),
                        onClickLeftIcon = {
                            navController.popBackStack()
                        },
                    ), titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_bundle_recommend_place)
                ),
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) // 스크롤 동작 연결
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(310.dp)
                    ) {
                        AsyncImage(
                            model = attraction.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color.White.copy(0.5F), Color.Transparent),
                                        start = Offset(Float.POSITIVE_INFINITY, 0F),
                                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                                    ),
                                )
                        )
                        Text(
                            modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
                            text = attraction.name,
                            style = HistourTheme.typography.head1.copy(HistourTheme.colors.gray900)
                        )
                    }
                }

                // 본문 내용
                item {
                    Text(
                        text = attraction.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        style = HistourTheme.typography.body3Reg.copy(color = HistourTheme.colors.gray800)
                    )
                }
            }
        }
    )
}