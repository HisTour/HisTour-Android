package com.startup.histour.presentation.main.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.dialog.HistourDialog
import com.startup.histour.presentation.widget.dialog.HistourDialogModel
import com.startup.histour.presentation.widget.dialog.TYPE
import com.startup.histour.presentation.widget.text.CharacterSelectableChipText
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.launch

@Composable
fun CharacterSettingScreen(
    navController: NavController,
    beforeCharacter: TempCharacter = TempCharacter(
        imgPath = "",
        name = "왕도깨비",
        introduce = "예로부터 왕의 옷을 입은 도깨비는 성질이 거만하고 말을 듣지 않아요.\n설명을 들으며 인내심을 길러봐요!"
    )
) {
    var selectedCharacter by remember {
        mutableStateOf<TempCharacter>(beforeCharacter)
    }
    val openDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        HisTourTopBar(
            model = HistourTopBarModel(
                leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                    leftIcons = listOf(HistourTopBarModel.TopBarIcon.BACK),
                    onClickLeftIcon = {
                        openDialog.value = true
                    }
                ),
                rightSectionType = HistourTopBarModel.RightSectionType.Text(
                    stringResId = R.string.save,
                    state = if (beforeCharacter != selectedCharacter) HistourTopBarModel.RightSectionType.Text.State.SAVE else HistourTopBarModel.RightSectionType.Text.State.INACTIVE,
                    onClickRightTextArea = {
                        // TODO 저장
                    },
                ),
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        SelectedCharacterDisplayView(
            character = selectedCharacter
        )
        SelectableCharacterPager(
            beforeSelectedItem = beforeCharacter, listOf(
                TempCharacter(
                    imgPath = "",
                    name = "왕도깨비",
                    introduce = "예로부터 왕의 옷을 입은 도깨비는 성질이 거만하고 말을 듣지 않아요.\n설명을 들으며 인내심을 길러봐요!"
                ), TempCharacter(
                    imgPath = "",
                    name = "어쩌구 도깨비",
                    introduce = "예로부터 왕의 옷을 입은 도깨비는 성질이 거만하고 말을 듣지 않아요.\n설명을 들으며 인내심을 길러봐요!"
                ), TempCharacter(
                    imgPath = "",
                    name = "응 도깨비",
                    introduce = "예로부터 왕의 옷을 입은 도깨비는 성질이 거만하고 말을 듣지 않아요.\n설명을 들으며 인내심을 길러봐요!"
                )
            )
        ) {
            selectedCharacter = it
        }
    }
    if (openDialog.value) {
        HistourDialog(
            histourDialogModel = HistourDialogModel(
                titleRes = R.string.dialog_change_close,
                descriptionRes = R.string.dialog_save_description,
                positiveButtonRes = R.string.dialog_continue,
                negativeButtonRes = R.string.dialog_exit,
                type = TYPE.DEFAULT
            ), onClickNegative = {
                openDialog.value = false
                navController.popBackStack()
            }, onClickPositive = {
                openDialog.value = false
            }
        )
    }
}

data class TempCharacter(val imgPath: String = "", val name: String = "", val introduce: String = "")

@Composable
fun SelectedCharacterDisplayView(character: TempCharacter) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        AsyncImage(
            model = character.imgPath,
            contentScale = ContentScale.Crop,
            onError = {
                Log.e("LMH", "DISPLAY IMAGE ERROR ${it.result.throwable}")
            },
            placeholder = painterResource(id = R.drawable.img_chat),
            modifier = Modifier
                .wrapContentWidth()
                /* 높이 확정 후 변경 */
                .height(225.dp)
                .noRippleClickable {
                },
            contentDescription = null,
        )
        Box(
            modifier = Modifier.padding(
                start = 24.dp,
                top = 17.dp,
                end = 24.dp,
                bottom = 24.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 18.dp)
            ) {
                Text(
                    text = character.introduce,
                    style = HistourTheme.typography.body2Reg.copy(
                        color = HistourTheme.colors.gray500
                    ),
                    modifier = Modifier
                        .background(color = HistourTheme.colors.gray100, shape = RoundedCornerShape(12.dp))
                        .padding(
                            start = 32.dp,
                            end = 32.dp,
                            top = 29.dp,
                            bottom = 20.dp
                        )
                )
            }
            Text(
                text = character.name,
                style = HistourTheme.typography.body2Bold.copy(
                    color = HistourTheme.colors.white000
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(color = HistourTheme.colors.gray800, shape = RoundedCornerShape(111.dp))
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectableCharacterPager(beforeSelectedItem: TempCharacter, list: List<TempCharacter>, onSelectItem: (TempCharacter) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = list.indexOf(beforeSelectedItem).takeIf { it != -1 } ?: 0, pageCount = { list.size })
    val localConfiguration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFF4B8), Color(0xFFFFF8D3)),
                    start = Offset(Float.POSITIVE_INFINITY, 0F),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(top = 27.dp, bottom = 23.dp),
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = pagerState,
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(horizontal = (localConfiguration.screenWidthDp / 3).dp),
        ) { page ->
            val character = list[page]
            if (pagerState.currentPage == page) {
                onSelectItem.invoke(character)
            }
            SelectableCharacterPagerItem(pagerState.currentPage == page, character) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(page)
                    onSelectItem.invoke(character)
                }
            }
        }
        Box(
            modifier = Modifier
                .width(45.dp)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFF5C3).copy(alpha = 0.0f), Color(0xFFFFF5C3)),
                        start = Offset(0F, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        )
        Box(
            modifier = Modifier
                .width(45.dp)
                .fillMaxHeight()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFF5C3), Color(0xFFFFF5C3).copy(alpha = 0.0f)),
                        start = Offset(0F, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        )
    }
}


@Composable
fun SelectableCharacterPagerItem(isSelected: Boolean, character: TempCharacter, onSelectItem: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = if (isSelected) HistourTheme.colors.white000 else HistourTheme.colors.gray50,
                shape = RoundedCornerShape(16.dp)
            )
            .border(width = if (isSelected) 2.dp else 0.dp, color = if (isSelected) HistourTheme.colors.green400 else Color.Unspecified, shape = RoundedCornerShape(16.dp))
            .padding(top = 28.dp, bottom = 19.dp)
            .noRippleClickable {
                onSelectItem.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(start = 19.dp, end = 19.dp),
            placeholder = painterResource(id = R.drawable.img_chat),
            model = character.imgPath,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(12.dp))
        CharacterSelectableChipText(isSelected, character.name)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCharacterSettingScreen() {
    HistourTheme {
        CharacterSettingScreen(navController = rememberNavController())
    }
}