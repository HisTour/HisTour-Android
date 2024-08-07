package com.startup.histour.presentation.widget.button

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.startup.histour.R
import com.startup.histour.ui.theme.HistourTheme

sealed class CTAMode {
    abstract val textStyle: TextStyle
    abstract val stokeWidth: Dp
    abstract val enable: Boolean

    @Composable
    abstract fun backgroundColor(): Color

    @Composable
    abstract fun buttonColor(): ButtonColors

    @Composable
    abstract fun stokeColor(): Color

    @Immutable
    data class Enable(override val textStyle: TextStyle, override val stokeWidth: Dp, override val enable: Boolean = true) : CTAMode() {

        @Composable
        override fun backgroundColor(): Color = HistourTheme.colors.green400

        @Composable
        override fun buttonColor(): ButtonColors = ButtonColors(
            contentColor = textStyle.color,
            containerColor = backgroundColor(),
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
        )

        @Composable
        override fun stokeColor(): Color = Color.Unspecified

        companion object {
            @Composable
            fun instance(): CTAMode {
                return Enable(textStyle = HistourTheme.typography.body1Bold.copy(color = HistourTheme.colors.white000), stokeWidth = Dp.Unspecified)
            }
        }
    }

    data class Disable(override val textStyle: TextStyle, override val stokeWidth: Dp, override val enable: Boolean = false) : CTAMode() {

        @Composable
        override fun backgroundColor(): Color = HistourTheme.colors.gray100

        @Composable
        override fun buttonColor(): ButtonColors = ButtonColors(
            contentColor = Color.Unspecified,
            containerColor = Color.Unspecified,
            disabledContentColor = textStyle.color,
            disabledContainerColor = backgroundColor(),
        )

        @Composable
        override fun stokeColor(): Color = Color.Unspecified

        companion object {
            @Composable
            fun instance(): CTAMode {
                return Disable(textStyle = HistourTheme.typography.body1Bold.copy(color = HistourTheme.colors.gray300), stokeWidth = Dp.Unspecified)
            }
        }
    }

    data class SubEnable(override val textStyle: TextStyle, override val stokeWidth: Dp = Dp.Unspecified, override val enable: Boolean = true) : CTAMode() {
        @Composable
        override fun backgroundColor(): Color = HistourTheme.colors.white000

        @Composable
        override fun buttonColor(): ButtonColors = ButtonColors(
            contentColor = textStyle.color,
            containerColor = backgroundColor(),
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
        )

        @Composable
        override fun stokeColor(): Color = HistourTheme.colors.green400

        companion object {
            @Composable
            fun instance(): CTAMode {
                return SubEnable(textStyle = HistourTheme.typography.body1Bold.copy(color = HistourTheme.colors.green400), stokeWidth = 1.dp)
            }
        }
    }

}

@Composable
fun CTAButton(
    @StringRes text: Int,
    mode: CTAMode,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = {
            onClick()
        },
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(mode.stokeWidth, mode.stokeColor()),
        shape = RoundedCornerShape(8.dp),
        elevation = null,
        colors = mode.buttonColor(),
        enabled = mode.enable,
    ) {
        Text(
            style = mode.textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 18.dp),
            text = stringResource(text),
        )
    }
}

@Composable
@Preview
private fun CTAButtonPreview() {
    HistourTheme {
        Column {
            CTAButton(R.string.app_name, CTAMode.Enable.instance()) {}
            Spacer(modifier = Modifier.height(10.dp))
            CTAButton(R.string.app_name, CTAMode.Disable.instance()) {}
            Spacer(modifier = Modifier.height(10.dp))
            CTAButton(R.string.app_name, CTAMode.SubEnable.instance()) {}
        }
    }
}