package com.startup.histour.presentation.login.ui

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.startup.histour.R
import com.startup.histour.presentation.login.viewmodel.LoginViewEvent
import com.startup.histour.presentation.login.viewmodel.LoginViewModel
import com.startup.histour.presentation.main.ui.MainActivity
import com.startup.histour.presentation.navigation.LoginScreens
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.util.extensions.openBrowser
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(LocalContext.current as ComponentActivity)
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.login_android)
    )
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            loginViewModel.event
                .filterIsInstance<LoginViewEvent>()
                .collectLatest { event ->
                    Log.e("LMH", "EVENT $event")
                    when (event) {
                        is LoginViewEvent.MoveToCharacterSettingView -> {
                            // 캐릭터 설정 화면으로 이동
                            navController.navigate(LoginScreens.Character.route)
                        }

                        is LoginViewEvent.MoveToPlaceSelectView -> {
                            // 장소 선택 화면으로 이동
                            navController.navigate(LoginScreens.Map.route)
                        }

                        is LoginViewEvent.MoveToMainView -> {
                            Intent(context, MainActivity::class.java).run {
                                (context as ComponentActivity?)?.also {
                                    it.startActivity(this)
                                    it.finish()
                                }
                            }
                        }

                        else -> Unit
                    }
                }
        }
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(111.dp))
            LottieAnimation(
                composition = preLoaderLottieComposition,
                iterations = LottieConstants.IterateForever,
                contentScale = ContentScale.Crop,
                restartOnPlay = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(160.dp, 200.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "여행 속 즐거운 미션과 함께 배우는\n역사 이야기",
                style = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray900),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_login),
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1F))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color(0xFFFEE500), RoundedCornerShape(12.dp))
                    .noRippleClickable {
                        loginViewModel.sendKakaoEvent()
                    }
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_kakao), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kakao 로 계속하기",
                    style = HistourTheme.typography.body1Bold.copy(color = HistourTheme.colors.gray900)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = stringResource(id = R.string.setting_menu_item_policy),
                textDecoration = TextDecoration.Underline,
                style = HistourTheme.typography.detail1Regular,
                modifier = Modifier
                    .noRippleClickable {
                        context.openBrowser("https://zippy-cake-826.notion.site/88e8a4673ad74e88a2221079602828e3?pvs=4")
                    })
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    HistourTheme {
        LoginScreen(rememberNavController())
    }
}