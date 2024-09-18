package com.startup.histour.presentation.widget.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.startup.histour.R
import com.startup.histour.presentation.util.extensions.ofMaxLength
import com.startup.histour.presentation.util.extensions.rippleClickable
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun ChatTextField(
    modifier: Modifier,
    maxLine: Int = Int.MAX_VALUE,
    @StringRes placeHolder: Int,
    maxLength: Int = Int.MAX_VALUE,
    text: String,
    textStyle: TextStyle,
    placeHolderStyle: TextStyle,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    onComplete: (() -> Unit)?
) {
    var currentText by remember {
        mutableStateOf(TextFieldValue(text))
    }
    val interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    }

    LaunchedEffect(text) {
        if (text != currentText.text) {
            currentText = TextFieldValue(text)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = HistourTheme.colors.white000, shape = RoundedCornerShape(25.dp))
            .padding(start = 16.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = currentText,
            onValueChange = { value ->
                value.ofMaxLength(maxLength).also { convertTextValue ->
                    if (currentText != convertTextValue) {
                        onValueChange.invoke(convertTextValue.text)
                        currentText = convertTextValue
                    }
                }
            },
            modifier = Modifier
                .weight(1F)
                .padding(top = 13.dp, bottom = 13.dp, end = 6.dp),
            readOnly = !enabled,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
            ),
            textStyle = textStyle,
            keyboardActions = KeyboardActions(
                onDone = {
                    onComplete?.invoke()
                },
                onGo = {
                    onComplete?.invoke()
                },
                onNext = {
                    onComplete?.invoke()
                },
            ),
            singleLine = maxLine == 1,
            maxLines = maxLine.takeIf { it > 0 } ?: 1,
            interactionSource = interactionSource,
            decorationBox = { textField ->
                Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                    if (currentText.text.isBlank()) {
                        Text(
                            text = stringResource(id = placeHolder),
                            style = placeHolderStyle
                        )
                    }
                    textField()
                }
            },
            enabled = enabled,
        )
        if (currentText.text.isNotBlank()) {
            if(enabled){
                Image(
                    painter = painterResource(id = R.drawable.btn_delete),
                    contentDescription = null,
                    modifier = Modifier.rippleClickable {
                        currentText = TextFieldValue("")
                        onValueChange("")
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = android.graphics.Color.BLUE.toLong())
@Composable
fun PreviewTextField() {
    HistourTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            ChatTextField(
                modifier = Modifier,
                text = "ㅇㄴㅁ",
                enabled = true,
                textStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray900),
                placeHolderStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray400),
                placeHolder = R.string.placeholder_tf_chat,
                onValueChange = {}
            ) {

            }
            Spacer(modifier = Modifier.height(10.dp))
            ChatTextField(
                modifier = Modifier,
                text = "asdjkashdkjashdkjashdkjashdkajshdaksjhdaksjdhakjsdjkhadskhj",
                enabled = true,
                textStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray900),
                placeHolderStyle = HistourTheme.typography.body2Reg.copy(color = HistourTheme.colors.gray400),
                placeHolder = R.string.placeholder_tf_chat,
                onValueChange = {}
            ) {

            }
        }

    }
}