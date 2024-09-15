package com.startup.histour.presentation.onboarding.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.presentation.main.model.CharacterViewEvent
import com.startup.histour.presentation.main.viewmodel.CharacterViewModel
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.navigation.LoginScreens
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
fun CharacterScreen(navController: NavController, characterViewModel: CharacterViewModel = hiltViewModel()) {
    val characterList by characterViewModel.state.characterList.collectAsState()
    var selectedCharacter by remember {
        mutableStateOf<CharacterModel?>(null)
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            characterViewModel.event
                .filterIsInstance<CharacterViewEvent>()
                .collectLatest { event ->
                    when (event) {
                        CharacterViewEvent.SuccessChangedCharacterAndPlaceSelect -> {
                            navController.navigate(LoginScreens.Map.route)
                        }

                        else -> {}
                    }
                }
        }
    }
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
                CharacterPagerView(characterList) {
                    selectedCharacter = it
                }
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
                    .noRippleClickable {
                        selectedCharacter?.let { characterViewModel.selectCharacter(it.id, true) }
                    }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterPagerView(
    list: List<CharacterModel>, onSelectItem: (CharacterModel) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { list.size })
    HorizontalPager(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), pageSpacing = 12.dp, beyondBoundsPageCount = 3, state = rememberPagerState(pageCount = { list.size }),
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) { page ->
        val character = list[page]
        if (pagerState.currentPage == page) {
            onSelectItem.invoke(character)
        }
        CharacterPagerViewItem(character)
    }
}

@Composable
fun CharacterPagerViewItem(model: CharacterModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(android.graphics.Color.parseColor(model.backgroundStart)), Color(android.graphics.Color.parseColor(model.backgroundEnd))),
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
            text = model.comment,
            style = HistourTheme.typography.body1Reg.copy(
                color = Color(android.graphics.Color.parseColor(model.commentColor))
            )
        )
        Spacer(modifier = Modifier.height(3.dp))
        AsyncImage(
            modifier = Modifier.size(230.dp),
            model = model.normalImageUrl,
            contentDescription = null
        )
        Box(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 19.dp)
            ) {
                Text(
                    text = model.description,
                    style = HistourTheme.typography.body1Reg.copy(
                        color = HistourTheme.colors.gray500
                    ),
                    modifier = Modifier
                        .background(color = HistourTheme.colors.white000, shape = RoundedCornerShape(11.dp))
                        .padding(
                            start = 22.dp,
                            end = 22.dp,
                            top = 27.dp,
                            bottom = 16.dp
                        )
                )
            }
            Text(
                text = model.name,
                style = HistourTheme.typography.body1Bold.copy(
                    color = HistourTheme.colors.white000
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(color = HistourTheme.colors.gray800, shape = RoundedCornerShape(111.dp))
                    .padding(horizontal = 19.dp, vertical = 8.dp)
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