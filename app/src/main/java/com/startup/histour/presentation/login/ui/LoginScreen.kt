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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.startup.histour.R
import com.startup.histour.presentation.login.viewmodel.LoginViewEvent
import com.startup.histour.presentation.login.viewmodel.LoginViewModel
import com.startup.histour.presentation.main.ui.MainActivity
import com.startup.histour.presentation.navigation.LoginScreens
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
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
                    }
                }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.weight(1F),
            //TODO set Logo
            painter = painterResource(id = R.drawable.img_chat),
            contentDescription = null
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(Color(0xFFFEE500), RoundedCornerShape(12.dp))
                .noRippleClickable {
                    loginViewModel.login()
                }
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.ic_kakao), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Kakao 로 계속하기", style = HistourTheme.typography.body1Bold.copy(color = HistourTheme.colors.gray900))
        }
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    HistourTheme {
        LoginScreen(rememberNavController())
    }
}