package com.startup.histour.presentation.widget.topbar


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.startup.histour.R
import com.startup.histour.core.extension.orZero
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.util.extensions.rippleClickable
import com.startup.histour.ui.theme.HistourTheme

data class HistourTopBarModel(
    val leftSectionType: LeftSectionType = LeftSectionType.Empty,
    val rightSectionType: RightSectionType = RightSectionType.Empty,
    val titleStyle: TitleStyle,
    val titleAlign: TitleAlign = TitleAlign.CENTER,
) {

    sealed interface LeftSectionType {

        data object Empty : LeftSectionType
        data class Icons(
            val leftIcons: List<TopBarIcon>,
            val onClickLeftIcon: (String) -> Unit,
        ) : LeftSectionType
    }

    sealed interface RightSectionType {

        data object Empty : RightSectionType
        data class Icons(
            val rightIcons: List<TopBarIcon>,
            val onClickRightIcon: (String) -> Unit,
        ) : RightSectionType

        data class Text(
            @StringRes val stringResId: Int,
            val state: State = State.FINISH,
            val onClickRightTextArea: () -> Unit,
        ) : RightSectionType {

            enum class State {
                FINISH,
                SAVE,
                COMPLETE,
                INACTIVE,
            }
        }
    }

    enum class TopBarIcon(
        @DrawableRes val resId: Int,
        @DrawableRes val contentDescription: Int? = null,
    ) {
        BACK(R.drawable.ic_arrow_left_back),
        SETTINGS(R.drawable.btn_setting)
    }

    sealed interface TitleStyle {

        data class Text(@StringRes val titleResId: Int) : TitleStyle
        data class TextWithIcon(val titleResId: String, @DrawableRes val icon: Int) :
            TitleStyle

        data class Image(val resId: Int) : TitleStyle
    }

    enum class TitleAlign(val align: TextAlign) {
        LEFT(TextAlign.Left),
        CENTER(TextAlign.Center),
    }
}

@Composable
private fun HisTourTopBarIconSection(
    icons: List<HistourTopBarModel.TopBarIcon>,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .width(48.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icons.forEach { icon ->
            Icon(
                modifier = Modifier
                    .rippleClickable { onClick(icon.name) },
                painter = painterResource(id = icon.resId),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun HisTourTopBarTitleSection(
    titleStyle: HistourTopBarModel.TitleStyle,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        when (titleStyle) {
            is HistourTopBarModel.TitleStyle.Text -> {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = titleStyle.titleResId),
                    style = HistourTheme.typography.body1Bold
                )
            }

            is HistourTopBarModel.TitleStyle.TextWithIcon -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Center, // 항상 중앙 정렬
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = "강원도 춘천시",
                        style = HistourTheme.typography.body1Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = titleStyle.icon),
                        contentDescription = titleStyle.icon.toString()
                    )
                }
            }

            is HistourTopBarModel.TitleStyle.Image -> {
                // todo
            }
        }
    }
}

@Composable
private fun HisTourTopBarLeftSection(
    leftSectionType: HistourTopBarModel.LeftSectionType,
) {
    when (leftSectionType) {
        is HistourTopBarModel.LeftSectionType.Empty -> {
            // do nothing
        }

        is HistourTopBarModel.LeftSectionType.Icons -> {
            HisTourTopBarIconSection(
                icons = leftSectionType.leftIcons,
                onClick = leftSectionType.onClickLeftIcon,
            )
        }
    }
}

@Composable
private fun HisTourTopBarRightSection(
    rightSectionType: HistourTopBarModel.RightSectionType,
) {
    when (rightSectionType) {
        is HistourTopBarModel.RightSectionType.Empty -> {
            // do nothing
        }

        is HistourTopBarModel.RightSectionType.Icons -> {
            HisTourTopBarIconSection(
                icons = rightSectionType.rightIcons,
                onClick = rightSectionType.onClickRightIcon,
            )
        }

        is HistourTopBarModel.RightSectionType.Text -> {
            val (textColor, clickEnabled) = when (rightSectionType.state) {
                HistourTopBarModel.RightSectionType.Text.State.FINISH -> {
                    HistourTheme.colors.red300 to true
                }

                HistourTopBarModel.RightSectionType.Text.State.SAVE -> {
                    HistourTheme.colors.green400 to true
                }

                HistourTopBarModel.RightSectionType.Text.State.COMPLETE -> {
                    HistourTheme.colors.blue300 to true
                }

                HistourTopBarModel.RightSectionType.Text.State.INACTIVE -> {
                    HistourTheme.colors.gray200 to false
                }
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(min = 56.dp)
                        .widthIn(min = 48.dp)
                        .noRippleClickable(
                            enabled = clickEnabled,
                            onClick = rightSectionType.onClickRightTextArea,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(rightSectionType.stringResId),
                        style = HistourTheme.typography.body2Bold,
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun HisTourTopBar(model: HistourTopBarModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = HistourTheme.colors.white000)
            .height(topBarDefaultHeight)
            .drawBottomBorder(
                strokeWidth = 0.5.dp,
                color = HistourTheme.colors.gray400
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = topBarPadding)
        ) {
            SubcomposeLayout { constraints ->
                val leftSectionPlaceable = subcompose("leftSection") {
                    HisTourTopBarLeftSection(model.leftSectionType)
                }.firstOrNull()?.measure(constraints)

                val rightSectionPlaceable = subcompose("rightSection") {
                    HisTourTopBarRightSection(model.rightSectionType)
                }.firstOrNull()?.measure(constraints)

                val totalWidth = constraints.maxWidth
                val totalHeight = constraints.maxHeight
                val leftIconsWidth = leftSectionPlaceable?.width.orZero()
                val rightIconsWidth = rightSectionPlaceable?.width.orZero()
                val titleAvailableWidth = when (model.titleAlign) {
                    HistourTopBarModel.TitleAlign.LEFT -> totalWidth - (leftIconsWidth + rightIconsWidth)
                    HistourTopBarModel.TitleAlign.CENTER ->
                        totalWidth - 2 * kotlin.math.max(
                            leftIconsWidth,
                            rightIconsWidth,
                        )
                }.let { if (it < 0) 0 else it }
                val leftPlacableX = 0
                val diffLeftRightWidth =
                    maxOf(leftIconsWidth, rightIconsWidth) - minOf(leftIconsWidth, rightIconsWidth)
                val titlePlaceAbleX =
                    leftPlacableX + leftIconsWidth + if (rightIconsWidth > leftIconsWidth) {
                        diffLeftRightWidth
                    } else {
                        0
                    }
                val rightPlaceAbleX =
                    leftPlacableX + leftIconsWidth + titleAvailableWidth + diffLeftRightWidth

                val titleSectionPlaceable = subcompose("titleSection") {
                    HisTourTopBarTitleSection(
                        titleStyle = model.titleStyle,
                    )
                }.first().measure(
                    Constraints(
                        minWidth = titleAvailableWidth,
                        maxWidth = titleAvailableWidth,
                        minHeight = totalHeight,
                        maxHeight = totalHeight,
                    ),
                )

                layout(totalWidth, totalHeight) {
                    leftSectionPlaceable?.placeRelative(x = leftPlacableX, y = 0)
                    titleSectionPlaceable.placeRelative(x = titlePlaceAbleX, y = 0)
                    rightSectionPlaceable?.placeRelative(x = rightPlaceAbleX, y = 0)
                }
            }
        }
    }
}

/**
 * 하단 stroke 그리는 함수
 */
fun Modifier.drawBottomBorder(strokeWidth: Dp, color: Color) = this.then(
    Modifier.drawWithContent {
        drawContent()
        drawLine(
            color = color,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = strokeWidth.toPx()
        )
    }
)

fun DrawScope.drawBottomBorder(strokeWidth: Dp, color: Color) {
    val strokeWidthPx = strokeWidth.toPx()
    drawLine(
        color = color,
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        strokeWidth = strokeWidthPx
    )
}

@Composable
@Preview(widthDp = 320)
private fun HisTourTopBarPreviewWidth320(
    @PreviewParameter(TopBarPreviewProvider::class) model: HistourTopBarModel,
) {
    HistourTheme {
        HisTourTopBar(model)
    }
}

@Composable
@Preview(widthDp = 375)
private fun HisTourTopBarPreviewWidth375(
    @PreviewParameter(TopBarPreviewProvider::class) model: HistourTopBarModel,
) {
    HistourTheme {
        HisTourTopBar(model)
    }
}

@Composable
@Preview(device = Devices.FOLDABLE)
private fun HisTourTopBarPreviewDeviceFoldable(
    @PreviewParameter(TopBarPreviewProvider::class) model: HistourTopBarModel,
) {
    HistourTheme {
        HisTourTopBar(model)
    }
}

private val topBarDefaultHeight = 60.dp
private val topBarPadding = 10.dp
