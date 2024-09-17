package com.startup.histour.presentation.onboarding.ui

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.startup.histour.R
import com.startup.histour.presentation.login.ui.LoginActivity
import com.startup.histour.presentation.navigation.MainScreens
import com.startup.histour.presentation.onboarding.model.SettingViewMoveEvent
import com.startup.histour.presentation.onboarding.viewmodel.SettingViewModel
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.util.extensions.openBrowser
import com.startup.histour.presentation.widget.dialog.HistourDialog
import com.startup.histour.presentation.widget.dialog.HistourDialogModel
import com.startup.histour.presentation.widget.dialog.TYPE
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavController, settingViewModel: SettingViewModel = hiltViewModel()) {
    val userInfo by settingViewModel.state.userInfo.collectAsState()
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingViewModel.event
                .filterIsInstance<SettingViewMoveEvent>()
                .collectLatest { event ->
                    Log.e("LMH", "GET SETTING EVENT")
                    if (event is SettingViewMoveEvent.MoveToLoginActivity) {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        activity?.finish()
                    }
                }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = HistourTheme.colors.white000)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HisTourTopBar(
            model = HistourTopBarModel(
                leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                    leftIcons = listOf(HistourTopBarModel.TopBarIcon.BACK),
                    onClickLeftIcon = {
                        navController.popBackStack()
                    }
                ),
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_setting)
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        ProfileItem(profilePath = userInfo.character.faceImageUrl, userName = userInfo.userName) {
            navController.navigate(MainScreens.NickNameChange.route + "/${userInfo.userName}")
        }
        Spacer(modifier = Modifier.height(18.dp))
        Column(
            modifier = Modifier
                .weight(1F)
                .background(color = HistourTheme.colors.gray100),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val openWithdrawalDialog = remember { mutableStateOf(false) }
            val openLogOutDialog = remember { mutableStateOf(false) }
            MenuItemView(list = MenuType.entries.map {
                when (it) {
                    MenuType.Version -> {
                        MenuItem(
                            type = it,
                            titleStrResId = R.string.setting_menu_item_version,
                            rightMenuType = RightMenuType.Text("v${context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()}")
                        )
                    }

                    MenuType.Policy -> {
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_policy, rightMenuType = RightMenuType.Icon()) {
                            /* TODO Move To 이용약관 */
                            context.openBrowser("https://zippy-cake-826.notion.site/4de57a048ffa4b078a72ae2c676789d9?pvs=4")
                        }
                    }

                    MenuType.Terms -> {
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_terms, rightMenuType = RightMenuType.Icon()) {
                            /* TODO Move To 개인정보 처리 방침 */
                            context.openBrowser("https://zippy-cake-826.notion.site/8214d9b45a42437682c8ff50e660f34b")
                        }
                    }

                    MenuType.Instagram -> {
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_instagram, rightMenuType = RightMenuType.Icon()) {
                            /* TODO Move To 인스타그램 */
                            context.openBrowser("https://zippy-cake-826.notion.site/ai-936923800f68461abda90b56e01d1b42")
                        }
                    }

                    MenuType.Logout -> {
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_logout, rightMenuType = RightMenuType.Not) {
                            /* TODO Move To 로그아웃 */
                            openLogOutDialog.value = true
                        }
                    }
                }
            })
            val color = HistourTheme.colors.gray400
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 24.dp, bottom = 32.dp)
                    .drawBehind {
                        val strokeWidth = (0.5).dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = color, // Bottom Border 색상
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .noRippleClickable {
                        openWithdrawalDialog.value = true
                    },
            ) {
                Text(stringResource(id = R.string.setting_menu_item_leave), style = HistourTheme.typography.detail2Regular.copy(color = HistourTheme.colors.gray400))
                Image(painter = painterResource(id = R.drawable.ic_unsubscribe), contentDescription = null)
            }

            if (openWithdrawalDialog.value) {
                HistourDialog(
                    histourDialogModel = HistourDialogModel(
                        titleRes = R.string.dialog_title_sign_out_title,
                        descriptionRes = R.string.dialog_title_sign_out_sub_title,
                        positiveButtonRes = R.string.dialog_title_sign_out_positive,
                        negativeButtonRes = R.string.dialog_title_sign_out_negative,
                        type = TYPE.DEFAULT
                    ),
                    onClickPositive = {
                        // TODO 탈퇴하기
                        settingViewModel.withdrawalAccount()
                        openWithdrawalDialog.value = false
                    },
                    onClickNegative = {
                        openWithdrawalDialog.value = false
                    },
                )
            }
            if (openLogOutDialog.value) {
                HistourDialog(
                    histourDialogModel = HistourDialogModel(
                        titleRes = R.string.dialog_title_log_out_title,
                        positiveButtonRes = R.string.dialog_continue,
                        negativeButtonRes = R.string.dialog_cancel,
                        type = TYPE.DEFAULT
                    ),
                    onClickPositive = {
                        // TODO 로그아웃
                        settingViewModel.logout()
                        openLogOutDialog.value = false
                    },
                    onClickNegative = {
                        openLogOutDialog.value = false
                    }
                )
            }
        }
    }
}

/* TODO 나중에 Image URL 로 해야함, 아마 카카오 프로필 URL? Name 도 카카오 Name */
@Composable
fun ProfileItem(profilePath: String, userName: String, onClickNickNameEdit: () -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Create references for the components
        val (profileImage, text, editIcon) = createRefs()
        AsyncImage(
            model = profilePath,
            contentScale = ContentScale.Crop,
            /* TODO 수정 필요 */
            onError = {
                Log.e("LMH", "DISPLAY IMAGE ERROR ${it.result.throwable}")
            },
            modifier = Modifier
                .size(90.dp)
                .constrainAs(profileImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .border(width = (1.5F).dp, color = HistourTheme.colors.green400, shape = CircleShape)
                .background(color = HistourTheme.colors.gray200, shape = CircleShape),
            contentDescription = null
        )
        Text(
            modifier = Modifier.constrainAs(text) {
                top.linkTo(profileImage.bottom, margin = 8.dp)
                start.linkTo(profileImage.start)
                end.linkTo(profileImage.end)
            },
            text = userName, style = HistourTheme.typography.head4.copy(color = HistourTheme.colors.gray900),
            textAlign = TextAlign.Center
        )

        Icon(
            modifier = Modifier
                .constrainAs(editIcon) {
                    top.linkTo(text.top)
                    bottom.linkTo(text.bottom)
                    start.linkTo(text.end)
                }
                .size(28.dp)
                .noRippleClickable {
                    onClickNickNameEdit.invoke()
                },
            painter = painterResource(id = R.drawable.ic_btn_edit),
            contentDescription = "nickname_change",
            tint = Color.Unspecified,
        )

    }
}

@Composable
fun MenuItemView(list: List<MenuItem>) {
    Column(
        modifier = Modifier
            .padding(top = 18.dp, start = 24.dp, end = 24.dp, bottom = 22.dp)
    ) {
        list.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = HistourTheme.colors.white000, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 24.dp)
                    .noRippleClickable {
                        item.clickEvent.invoke()
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 14.dp), text = stringResource(id = item.titleStrResId),
                    style = HistourTheme.typography.body1Reg.copy(color = if (item.type == MenuType.Logout) HistourTheme.colors.red300 else HistourTheme.colors.gray900)
                )
                when (item.rightMenuType) {
                    RightMenuType.Not -> {}
                    is RightMenuType.Text -> {
                        Text(
                            text = item.rightMenuType.content,
                            style = HistourTheme.typography.detail1Regular.copy(color = HistourTheme.colors.gray400)
                        )
                    }

                    is RightMenuType.Icon -> {
                        Image(painter = painterResource(id = item.rightMenuType.iconResId), contentDescription = null)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

enum class MenuType {
    Version, Terms, Policy, Instagram, Logout
}

data class MenuItem(val type: MenuType, @StringRes val titleStrResId: Int, val rightMenuType: RightMenuType = RightMenuType.Icon(), val clickEvent: () -> Unit = {})

sealed interface RightMenuType {
    data class Text(val content: String) : RightMenuType
    data class Icon(@DrawableRes val iconResId: Int = R.drawable.ic_enter_large) : RightMenuType
    data object Not : RightMenuType
}

@Preview
@Composable
fun PreviewSettingScreen() {
    HistourTheme {
        SettingScreen(navController = rememberNavController())
    }
}