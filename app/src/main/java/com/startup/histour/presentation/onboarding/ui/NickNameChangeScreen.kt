package com.startup.histour.presentation.onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.startup.histour.R
import com.startup.histour.presentation.onboarding.model.NickNameChangedEvent
import com.startup.histour.presentation.onboarding.viewmodel.SettingViewModel
import com.startup.histour.presentation.util.ServiceConst
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.util.extensions.ofMaxLength
import com.startup.histour.presentation.widget.topbar.HisTourTopBar
import com.startup.histour.presentation.widget.topbar.HistourTopBarModel
import com.startup.histour.ui.theme.HistourTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch


@Composable
fun NickNameChangeScreen(navController: NavController, snackBarHostState: SnackbarHostState, beforeNickName: String, settingViewModel: SettingViewModel = hiltViewModel()) {
    var currentText by remember {
        mutableStateOf(TextFieldValue(beforeNickName))
    }
    val interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingViewModel.event
                .filterIsInstance<NickNameChangedEvent>()
                .collectLatest { event ->
                    when (event) {
                        NickNameChangedEvent.OnChangedNickName -> {
                            navController.popBackStack()
                            snackBarHostState.showSnackbar("이름이 변경되었어요")
                        }
                    }
                }
        }
    }
    val focusRequester = FocusRequester()
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .background(HistourTheme.colors.white000)
    ) {
        HisTourTopBar(
            model = HistourTopBarModel(
                leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                    leftIcons = listOf(HistourTopBarModel.TopBarIcon.BACK),
                    onClickLeftIcon = {
                        navController.popBackStack()
                    }
                ),
                rightSectionType = HistourTopBarModel.RightSectionType.Text(
                    stringResId = R.string.save,
                    state = HistourTopBarModel.RightSectionType.Text.State.SAVE,
                    onClickRightTextArea = {
                        settingViewModel.changeUserNickName(currentText.text)
                    },
                ),
                titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_setting)
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(modifier = Modifier.padding(start = 24.dp), text = "이름", style = HistourTheme.typography.body2Bold.copy(color = HistourTheme.colors.gray900))
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = HistourTheme.colors.gray700,
                        shape = CircleShape,
                    )
                    .background(
                        color = HistourTheme.colors.white000,
                        shape = CircleShape,
                    )
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    value = currentText,
                    onValueChange = { value ->
                        value.ofMaxLength(ServiceConst.MAX_LENGTH).let { convertTextValue ->
                            if (currentText != convertTextValue) {
                                currentText = convertTextValue
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password,
                    ),
                    visualTransformation = VisualTransformation.None,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusRequester.freeFocus()
                        },
                        onGo = {
                            focusRequester.freeFocus()
                        },
                        onNext = {
                            focusRequester.freeFocus()
                        },
                    ),
                    modifier = Modifier
                        .weight(1F, true)
                        .focusRequester(focusRequester),
                    textStyle = HistourTheme.typography.body3Medi.copy(color = HistourTheme.colors.gray900),
                    singleLine = true,
                    maxLines = 1,
                    interactionSource = interactionSource,
                    decorationBox = { textField ->
                        textField()
                    },
                )
                if (currentText.text.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_delete),
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            currentText = TextFieldValue("")
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${currentText.text.length}/${ServiceConst.MAX_LENGTH}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                textAlign = TextAlign.End,
                style = HistourTheme.typography.detail2Regular.copy(color = HistourTheme.colors.gray400)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewNickNameChangeScreen() {
    HistourTheme {
        /*NickNameChangeScreen(navController = rememberNavController())*/
    }
}
