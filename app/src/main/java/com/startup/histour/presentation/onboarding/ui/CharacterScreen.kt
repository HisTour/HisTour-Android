package com.startup.histour.presentation.onboarding.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.startup.histour.R
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun CharacterScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = HistourTheme.colors.white000)
            .padding(top = 31.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (content, pager, button) = createRefs()
            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        bottom.linkTo(pager.top)
                        height = Dimension.preferredWrapContent
                    }
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = stringResource(id = R.string.on_boarding_character_title),
                        style = HistourTheme.typography.head1.copy(color = HistourTheme.colors.gray900)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.on_boarding_character_sub_title),
                        style = HistourTheme.typography.body1Reg.copy(color = HistourTheme.colors.gray500)
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
            Column(
                modifier = Modifier.constrainAs(pager) {
                    top.linkTo(content.bottom)
                    bottom.linkTo(button.top)
                    height = Dimension.fillToConstraints
                }) {
                CharacterPagerView()
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = stringResource(id = R.string.on_boarding_character_select),
                style = HistourTheme.typography.body2Bold.copy(
                    color = HistourTheme.colors.white000
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, 16.dp)
                    .background(color = HistourTheme.colors.green400, shape = RoundedCornerShape(12.dp))
                    .padding(vertical = 15.dp)
            )
        }
    }
}

data class TempCharacter(
    val name: String,
    val introduce: String,
    val introduceColor: Color,
    val characterDrawable: Painter,
    val description: String,
    val backgroundStartColor: Color,
    val backgroundEndColor: Color
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterPagerView(
    list: List<TempCharacter> = listOf(
        TempCharacter(
            name = "왕도깨비",
            introduce = "경주에 온 걸 환영하노라 낯선 이여",
            introduceColor = HistourTheme.colors.yellow700,
            characterDrawable = painterResource(id = R.drawable.ic_launcher_foreground),
            description = "예로부터 왕의 옷을 입은 도깨비는 성질이 거만하고 말을 듣지 않아요.\n설명을 들으며 인내심을 길러봐요!",
            backgroundStartColor = HistourTheme.colors.yellow100,
            backgroundEndColor = HistourTheme.colors.yellow200
        ),
        TempCharacter(
            name = "왕도깨비",
            introduce = "경주에 온 걸 환영하노라 낯선 이여",
            introduceColor = HistourTheme.colors.yellow700,
            characterDrawable = painterResource(id = R.drawable.ic_launcher_foreground),
            description = "예로부터 왕의 옷을 입은 도깨비는 성질이 거만하고 말을 듣지 않아요.\n설명을 들으며 인내심을 길러봐요!",
            backgroundStartColor = HistourTheme.colors.yellow100,
            backgroundEndColor = HistourTheme.colors.yellow200
        ),
        TempCharacter(
            name = "왕도깨비",
            introduce = "경주에 온 걸 환영하노라 낯선 이여",
            introduceColor = HistourTheme.colors.yellow700,
            characterDrawable = painterResource(id = R.drawable.ic_launcher_foreground),
            description = "예로부터 왕의 옷을 입은 도깨비는 성질이 거만하고 말을 듣지 않아요.\n설명을 들으며 인내심을 길러봐요!",
            backgroundStartColor = HistourTheme.colors.yellow100,
            backgroundEndColor = HistourTheme.colors.yellow200
        )
    ),
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), pageSpacing = 12.dp, beyondBoundsPageCount = 3, state = rememberPagerState(pageCount = { list.size }),
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        CharacterPagerViewItem(list[it])
    }
}

@Composable
fun CharacterPagerViewItem(model: TempCharacter) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(model.backgroundStartColor, model.backgroundEndColor),
                    start = Offset.Zero,
                    end = Offset(0F, Float.POSITIVE_INFINITY)
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                top = 38.dp,
                bottom = 31.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier,
            text = model.introduce,
            style = HistourTheme.typography.body2Reg.copy(
                color = model.introduceColor
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = model.characterDrawable,
            contentDescription = null
        )
        Box(
            modifier = Modifier.padding(
                start = 26.dp,
                end = 26.dp,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 18.dp)
            ) {
                Text(
                    text = model.description,
                    style = HistourTheme.typography.body2Reg.copy(
                        color = HistourTheme.colors.gray500
                    ),
                    modifier = Modifier
                        .background(color = HistourTheme.colors.white000, shape = RoundedCornerShape(11.dp))
                        .padding(
                            start = 32.dp,
                            end = 32.dp,
                            top = 29.dp,
                            bottom = 20.dp
                        )
                )
            }
            Text(
                text = model.name,
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

@Preview
@Composable
fun PreviewCharacterScreen() {
    HistourTheme {
        CharacterScreen(navController = rememberNavController())
    }
}