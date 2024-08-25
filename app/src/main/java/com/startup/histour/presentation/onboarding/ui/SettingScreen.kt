package com.startup.histour.presentation.onboarding.ui

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun SettingScreen(navController: NavController) {

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
                    onClickLeftIcon = {}
                ),
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_setting)
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        ProfileItem(profilePath = "", userName = "깨비도")
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
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_version, rightMenuType = RightMenuType.Text(""))
                    }

                    MenuType.Policy -> {
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_policy) {
                            /* TODO Move To 이용약관 */
                        }
                    }

                    MenuType.Terms -> {
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_terms) {
                            /* TODO Move To 개인정보 처리 방침 */
                        }
                    }

                    MenuType.Instagram -> {
                        MenuItem(type = it, titleStrResId = R.string.setting_menu_item_instagram) {
                            /* TODO Move To 인스타그램 */
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
                    ), onClickNegative = {
                        openWithdrawalDialog.value = false
                    }, onClickPositive = {
                        // TODO 탈퇴하기
                        openWithdrawalDialog.value = false
                    }
                )
            }
            if (openLogOutDialog.value) {
                HistourDialog(
                    histourDialogModel = HistourDialogModel(
                        titleRes = R.string.dialog_title_log_out_title,
                        positiveButtonRes = R.string.dialog_continue,
                        negativeButtonRes = R.string.dialog_cancel,
                        type = TYPE.DEFAULT
                    ), onClickNegative = {
                        openLogOutDialog.value = false
                    }, onClickPositive = {
                        // TODO 로그아웃
                        openLogOutDialog.value = false
                    }
                )
            }
        }
    }
}

/* TODO 나중에 Image URL 로 해야함, 아마 카카오 프로필 URL? Name 도 카카오 Name */
@Composable
fun ProfileItem(profilePath: String, userName: String) {
    AsyncImage(
        model = profilePath,
        contentScale = ContentScale.Crop,
        /* TODO 수정 필요 */
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        onError = {
            Log.e("LMH", "DISPLAY IMAGE ERROR ${it.result.throwable}")
        },
        modifier = Modifier
            .size(90.dp)
            .border(width = (1.5F).dp, color = HistourTheme.colors.green400, shape = CircleShape)
            .background(color = Color.Transparent, shape = CircleShape),
        contentDescription = null
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(userName, style = HistourTheme.typography.head4.copy(color = HistourTheme.colors.gray900))
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
                    }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 14.dp), text = stringResource(id = item.titleStrResId),
                    style = HistourTheme.typography.body1Reg.copy(color = if (item.type == MenuType.Logout) HistourTheme.colors.red300 else HistourTheme.colors.gray900)
                )
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