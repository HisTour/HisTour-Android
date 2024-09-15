package com.startup.histour.presentation.widget.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.startup.histour.R
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.ui.theme.HistourTheme

data class HistourDialogModel(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int? = null,
    @StringRes val positiveButtonRes: Int,
    @StringRes val negativeButtonRes: Int,
    val type: TYPE = TYPE.DEFAULT
)

enum class TYPE {
    DEFAULT, REQUEST
}

@Composable
fun HistourDialog(
    histourDialogModel: HistourDialogModel,
    onClickNegative: () -> Unit,
    onClickPositive: (String?) -> Unit
) {

    var report by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onClickNegative() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = HistourTheme.colors.white000),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 4.dp, start = 24.dp, end = 24.dp)
                    .background(HistourTheme.colors.white000),
            ) {
                TextArea(model = histourDialogModel)
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
                when (histourDialogModel.type) {
                    TYPE.DEFAULT -> {}
                    TYPE.REQUEST -> {
                        CustomTextField { report = it }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(13.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .height(48.dp)
                        .wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .noRippleClickable {
                                onClickNegative.invoke()
                            },
                        text = stringResource(id = histourDialogModel.negativeButtonRes),
                        style = HistourTheme.typography.body2Bold,
                        textAlign = TextAlign.Center,
                        color = HistourTheme.colors.gray300,
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .noRippleClickable {
                                onClickPositive.invoke(if (histourDialogModel.type == TYPE.REQUEST) report else null)
                            },
                        text = stringResource(id = histourDialogModel.positiveButtonRes),
                        style = HistourTheme.typography.body2Bold,
                        textAlign = TextAlign.Center,
                        color = HistourTheme.colors.green400,
                    )
                }
            }
        }
    }
}

@Composable
private fun TextArea(model: HistourDialogModel) {
    Text(
        text = stringResource(id = model.titleRes),
        textAlign = TextAlign.Start,
        style = HistourTheme.typography.body1Bold
    )
    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
    )
    model.descriptionRes?.let {
        Text(
            text = stringResource(id = it),
            textAlign = TextAlign.Start,
            color = HistourTheme.colors.gray600,
            style = HistourTheme.typography.detail2Regular
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        placeholder = {
            Text(
                modifier = Modifier.offset(y = (-4).dp),
                text = stringResource(id = R.string.dialog_spot_hint),
                style = HistourTheme.typography.detail2Regular,
                color = HistourTheme.colors.gray300
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(HistourTheme.colors.gray100, RoundedCornerShape(8.dp))
            .wrapContentHeight(),
        textStyle = HistourTheme.typography.detail2Regular,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = false,
        minLines = 4,
        maxLines = 4,
    )
}


@Composable
@Preview(device = Devices.PHONE)
private fun HisTourDialogPreview() {
    HistourTheme {
//            HistourDialog(
//                HistourDialogModel(
//                    title = R.string.dialog_change_close,
//                    description = R.string.dialog_save_description,
//                    positiveButton = R.string.dialog_continue,
//                    negativeButton = R.string.dialog_exit,
//                ),
//                {}, {}
//            )
        HistourDialog(
            HistourDialogModel(
                titleRes = R.string.dialog_spot_request,
                descriptionRes = R.string.dialog_spot_request_description,
                positiveButtonRes = R.string.dialog_request,
                negativeButtonRes = R.string.dialog_close,
                type = TYPE.REQUEST
            ),
            {}, {}
        )
    }
}

@Composable
@Preview(device = Devices.FOLDABLE)
private fun HisTourDialogFoldablePreview() {
    HistourTheme {
        HistourDialog(
            HistourDialogModel(
                titleRes = R.string.dialog_spot_request,
                descriptionRes = R.string.dialog_spot_request_description,
                positiveButtonRes = R.string.dialog_request,
                negativeButtonRes = R.string.dialog_close,
                type = TYPE.REQUEST
            ),
            {}, {}
        )
    }
}

@Composable
@Preview(device = Devices.TABLET)
private fun HisTourDialogTabletPreview() {
    HistourTheme {
        HistourDialog(
            HistourDialogModel(
                titleRes = R.string.dialog_spot_request,
                descriptionRes = R.string.dialog_spot_request_description,
                positiveButtonRes = R.string.dialog_request,
                negativeButtonRes = R.string.dialog_close,
                type = TYPE.REQUEST
            ),
            {}, {}
        )
    }
}