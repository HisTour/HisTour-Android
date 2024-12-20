package com.startup.histour.presentation.onboarding.ui

import CTAImageButton
import CTAImageButtonModel
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.paint
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.startup.histour.R
import com.startup.histour.presentation.login.ui.LoginActivity
import com.startup.histour.presentation.main.ui.MainActivity
import com.startup.histour.presentation.onboarding.model.Place
import com.startup.histour.presentation.onboarding.model.TravelMapViewEvent
import com.startup.histour.presentation.onboarding.viewmodel.TravelMapViewModel
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.button.CTAButton
import com.startup.histour.presentation.widget.button.CTAMode
import com.startup.histour.presentation.widget.dialog.HistourDialog
import com.startup.histour.presentation.widget.dialog.HistourDialogModel
import com.startup.histour.presentation.widget.dialog.TYPE
import com.startup.histour.presentation.widget.progressbar.HistourProgressBar
import com.startup.histour.presentation.widget.progressbar.HistourProgressBarModel
import com.startup.histour.presentation.widget.progressbar.ProgressbarType
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
fun OnBoardingMapScreen(navController: NavController, snackBarHostState: SnackbarHostState, travelMapViewModel: TravelMapViewModel = hiltViewModel()) {
    val userInfo by travelMapViewModel.state.userInfo.collectAsState()
    var selectedButtonPosition by remember { mutableStateOf<Offset?>(null) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_2)
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

    BackHandler {

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
                style = HistourTheme.typography.head1.copy(HistourTheme.colors.gray900)
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

                // 광화문 버튼
                TransParentMapButton(
                    modifier = Modifier
                        .width(120.dp)
                        .height(100.dp)
                        .offset(x = 45.dp, y = 240.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(76f, 190f)
                    Log.e("hi", "경복궁")
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar("아직 준비 중이에요! \uD83D\uDEA7")
                    }
                }

                // 남한산성 버튼
                TransParentMapButton(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .offset(x = 190.dp, y = 200.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(217f, 150f)
                    Log.e("hi", "남한산성")
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar("아직 준비 중이에요! \uD83D\uDEA7")
                    }
                }

                // 수원 버튼
                TransParentMapButton(
                    modifier = Modifier
                        .width(120.dp)
                        .height(110.dp)
                        .offset(x = 75.dp, y = 370.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(105f, 315f)
                    selectedPlace = travelMapViewModel.state.placeList.value.find { it.placeId == 1 }
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
                    selectedButtonPosition = Offset(246f, 330f)
                    Log.e("hi", "경주")
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar("아직 준비 중이에요! \uD83D\uDEA7")
                    }
                }

                // 제주
                TransParentMapButton(
                    modifier = Modifier
                        .width(160.dp)
                        .height(90.dp)
                        .offset(x = 35.dp, y = 525.dp)
                        .zIndex(1f),
                ) {
                    selectedButtonPosition = Offset(77f, 460f)
                    Log.e("hi", "제주")
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar("아직 준비 중이에요! \uD83D\uDEA7")
                    }
                }

                selectedButtonPosition?.let { position ->
                    Box(
                        modifier = Modifier
                            .offset(x = position.x.dp, y = position.y.dp)
                            .paint(painter = painterResource(id = R.drawable.ic_spot))
                            .size(60.dp, 65.dp)
                            .zIndex(2f),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = userInfo.character.faceImageUrl, // 선택된 위치를 나타내는 이미지 리소스
                            contentDescription = "ggabi_marker",
                            modifier = Modifier
                                .size(60.dp) // 이미지 크기 조정
                        )
                    }
                }
            }

        }

        Row(
            modifier = Modifier
                .padding(end = 24.dp, bottom = 45.dp)
                .height(50.dp)
                .wrapContentWidth()
                .background(color = HistourTheme.colors.green400, shape = CircleShape)
                .align(Alignment.BottomEnd)
                .noRippleClickable {
                    selectedPlace = travelMapViewModel.state.placeList.value
                        .find { it.placeId == 1 }
                        ?.also {
                            selectedButtonPosition = Offset(105f, 315f)
                        }
                }
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
        selectedPlace?.let {
            PlaceBottomSheetView(it, onDismiss = {
                selectedButtonPosition = null
                selectedPlace = null
            }) { place ->
                travelMapViewModel.selectPlace(place.placeId)
                selectedPlace = null
                selectedButtonPosition = null
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
                modifier = Modifier.size(247.dp),
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
                content?.takeIf { it.isNotBlank() }?.let {
                    travelMapViewModel.callRecommendPlace(content)
                }
            },
            onClickNegative = {
                openRecommendPlaceDialog.value = false
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceBottomSheetView(place: Place = Place.orEmpty().copy(name = "수원화성"), onDismiss: () -> Unit, onSelectPlace: (Place) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            sheetState.show()
            sheetState.expand()
        }
    }
    ModalBottomSheet(
        contentColor = HistourTheme.colors.white000,
        containerColor = HistourTheme.colors.white000,
        sheetState = sheetState,
        onDismissRequest = { onDismiss.invoke() }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 60.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.suwon), contentDescription = null, modifier = Modifier.size(95.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(text = place.name, style = HistourTheme.typography.head3.copy(color = HistourTheme.colors.gray900))
                    Text(text = place.description, style = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray600))
                }
            }
            HistourProgressBar(
                histourProgressBarModel = HistourProgressBarModel(
                    totalStep = place.totalMissionCount,
                ),
                progress = kotlin.runCatching { (place.clearedMissionCount.toFloat() / place.totalMissionCount.toFloat()) }.getOrNull().takeIf { it != null && it >= 0F } ?: 0F,
                currentStep = place.clearedMissionCount,
                progressbarType = ProgressbarType.DEFAULT
            )
            Spacer(modifier = Modifier.height(32.dp))
            CTAButton(text = R.string.on_boarding_choice_place, mode = CTAMode.Enable.instance()) {
                onSelectPlace.invoke(place)
            }
        }
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