package com.startup.histour.presentation.onboarding.ui

import CTAImageButton
import CTAImageButtonModel
import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.startup.histour.R
import com.startup.histour.presentation.login.ui.LoginActivity
import com.startup.histour.presentation.main.ui.MainActivity
import com.startup.histour.presentation.onboarding.model.TravelMapViewEvent
import com.startup.histour.presentation.onboarding.viewmodel.TravelMapViewModel
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.dialog.HistourDialog
import com.startup.histour.presentation.widget.dialog.HistourDialogModel
import com.startup.histour.presentation.widget.dialog.TYPE
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch


@Composable
fun OnBoardingMapScreen(navController: NavController, snackBarHostState: SnackbarHostState, travelMapViewModel: TravelMapViewModel = hiltViewModel()) {

    var selectedButtonPosition by remember { mutableStateOf<Offset?>(null) }

    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )
    var isPlaceSelectSuccess by remember { mutableStateOf(false) }


    val openRecommendPlaceDialog = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            travelMapViewModel.event
                .filterIsInstance<TravelMapViewEvent>()
                .collectLatest { event ->
                    when (event) {
                        TravelMapViewEvent.MoveToMainActivity -> {
                            isPlaceSelectSuccess = true
                        }

                        is TravelMapViewEvent.ShowToast -> {
                            snackBarHostState.showSnackbar(event.msg)
                        }
                    }
                }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HistourTheme.colors.bgblue),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(154.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            HistourTheme.colors.white000,
                            HistourTheme.colors.white000.copy(alpha = 0f)
                        ),
                        startY = 0f,
                        endY = with(LocalDensity.current) { 154.dp.toPx() }
                    )
                )
                .zIndex(1f)
                .padding(top = 40.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.on_boarding_map_title),
                style = HistourTheme.typography.head1
            )

            CTAImageButton(
                modifier = Modifier,
                model = CTAImageButtonModel(
                    textId = R.string.on_boarding_request_spot,
                    drawableId = R.drawable.ic_letter
                )
            ) {
                openRecommendPlaceDialog.value = true
            }
        }
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(780.dp)
                    .align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_total_map),
                    contentDescription = "Map Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                // 경복궁 버튼
                TransParentMapButton(
                    modifier = Modifier
                        .width(120.dp)
                        .height(100.dp)
                        .offset(x = 45.dp, y = 240.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(45f, 240f)
                    Log.e("hi", "경복궁")
                }

                // 남한산성 버튼
                TransParentMapButton(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .offset(x = 190.dp, y = 200.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(190f, 200f)
                    Log.e("hi", "남한산성")
                }

                // 수원 버튼
                TransParentMapButton(
                    modifier = Modifier
                        .width(120.dp)
                        .height(110.dp)
                        .offset(x = 75.dp, y = 370.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(75f, 370f)
                    travelMapViewModel.selectPlace(1)
                    Log.e("hi", "수원")
                }

                // 경주
                TransParentMapButton(
                    modifier = Modifier
                        .width(90.dp)
                        .height(170.dp)
                        .offset(x = 230.dp, y = 370.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(230f, 370f)
                    Log.e("hi", "경주")
                }

                // 제주
                TransParentMapButton(
                    modifier = Modifier
                        .width(160.dp)
                        .height(90.dp)
                        .offset(x = 35.dp, y = 523.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(35f, 523f)
                    Log.e("hi", "제주")
                }

                selectedButtonPosition?.let { position ->
                    Image(
                        painter = painterResource(id = R.drawable.img_chat), // 선택된 위치를 나타내는 이미지 리소스
                        contentDescription = "ggabi_marker",
                        modifier = Modifier
                            .size(40.dp) // 이미지 크기 조정
                            .offset(x = position.x.dp, y = position.y.dp)
                            .zIndex(2f)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .height(50.dp)
                    .wrapContentWidth()
                    .background(color = HistourTheme.colors.green400, shape = CircleShape)
                    .align(Alignment.BottomEnd)
                    .noRippleClickable { }
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_dice),
                    contentDescription = "dice"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(R.string.on_boarding_choice_random),
                    color = HistourTheme.colors.white000,
                    style = HistourTheme.typography.head4
                )
            }
        }
    }
    if (isPlaceSelectSuccess) {
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            delay(2000)
            if (context is LoginActivity) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as? Activity)?.finish()
            } else {
                navController.popBackStack()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#B2000000"))),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = preLoaderLottieComposition,
                iterations = LottieConstants.IterateForever,
                restartOnPlay = true,
                modifier = Modifier.size(247.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "나를 따르라~", style = HistourTheme.typography.head2.copy(HistourTheme.colors.white000))
        }
    }
    if (openRecommendPlaceDialog.value) {
        HistourDialog(
            histourDialogModel = HistourDialogModel(
                titleRes = R.string.dialog_spot_request,
                descriptionRes = R.string.dialog_spot_request_description,
                positiveButtonRes = R.string.dialog_request,
                negativeButtonRes = R.string.dialog_close,
                type = TYPE.REQUEST
            ),
            onClickPositive = { content ->
                openRecommendPlaceDialog.value = false
                content?.let {
                    travelMapViewModel.callRecommendPlace(content)
                }
            },
            onClickNegative = {
                openRecommendPlaceDialog.value = false
            },
        )
    }
}


@Composable
fun TransParentMapButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .noRippleClickable { onClick.invoke() }
            .background(Color.Transparent)
    )
}


@Preview
@Composable
fun PreviewOnBoardingScreen() {
    HistourTheme {
        OnBoardingMapScreen(navController = rememberNavController(), SnackbarHostState())
    }
}