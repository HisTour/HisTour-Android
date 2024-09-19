package com.startup.histour.presentation.widget.progressbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.startup.histour.R
import com.startup.histour.ui.theme.HistourTheme

data class HistourProgressBarModel(
    val totalStep: Int,
    @DrawableRes val dotImage: Int = R.drawable.ic_progress
)

private const val progressDefaultHeight = 44
private const val progressWithImage = 42
private const val progressWithTooltipHeight = 85
private const val progressBarHeight = 16
private const val submissionProgressBarHeight = 12
private const val tooltipWidth = 53

enum class ProgressbarType {
    DEFAULT,
    IMAGE,
    TOOLTIP,
    SUBMISSION
}


@Composable
fun HistourProgressBar(
    histourProgressBarModel: HistourProgressBarModel,
    progress: Float = 0.0f,
    currentStep: Int = 0,
    progressbarType: ProgressbarType = ProgressbarType.DEFAULT,
    backgroundColor: Color = HistourTheme.colors.gray50,
    progressColor: Color = HistourTheme.colors.green200,
    animationDuration: Int = 1000,
) {

    val height = getProgressBarHeight(progressbarType)
    val calculatedProgress =
        calculateProgress(progressbarType, currentStep, histourProgressBarModel.totalStep, progress)
    val animatedProgress by animateFloatAsState(
        targetValue = calculatedProgress,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )
    val boxWidth = remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                boxWidth.intValue = coordinates.size.width
            }
    ) {
        if (progressbarType == ProgressbarType.DEFAULT) {
            DefaultProgressHeader(currentStep, histourProgressBarModel.totalStep)
        }

        ProgressBar(
            type = progressbarType,
            progress = animatedProgress,
            backgroundColor = backgroundColor,
            progressColor = progressColor,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        ProgressIndicator(
            progressbarType = progressbarType,
            animatedProgress = animatedProgress,
            calculatedProgress = calculatedProgress,
            boxWidth = boxWidth.intValue,
            dotImage = histourProgressBarModel.dotImage,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

/**
 * 전체진행도를 나타낼 배경 프로그래스바
 */
@Composable
private fun ProgressBar(
    type: ProgressbarType = ProgressbarType.DEFAULT,
    progress: Float,
    backgroundColor: Color,
    progressColor: Color,
    modifier: Modifier = Modifier
) {
    val height = if (type == ProgressbarType.SUBMISSION) submissionProgressBarHeight
    else progressBarHeight
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(height.dp)
            )
            .height(height.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = progressColor,
                    shape = RoundedCornerShape(height.dp)
                )
                .height(height.dp)
                .fillMaxWidth(progress.coerceIn(0f, 1f))
        )
    }
}

/**
 * 진행도에 따라 채워지는 프로그래스바
 */
@Composable
private fun ProgressIndicator(
    progressbarType: ProgressbarType,
    animatedProgress: Float,
    calculatedProgress: Float,
    boxWidth: Int,
    dotImage: Int,
    modifier: Modifier = Modifier
) {
    val imageSize = progressDefaultHeight.dp
    val progressBarWidth = boxWidth.toFloat()

    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .width(imageSize)
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .graphicsLayer {
                    val maxOffset = progressBarWidth - imageSize.toPx()
                    translationX = animatedProgress * maxOffset
                    translationY = 24.0f
                }
        ) {
            Column {
                if (progressbarType == ProgressbarType.TOOLTIP) {
                    ProgressTooltip(calculatedProgress)
                }
                if (progressbarType == ProgressbarType.IMAGE || progressbarType == ProgressbarType.TOOLTIP) {
                    ProgressImage(dotImage)
                }
            }
        }
    }
}

/**
 * 방망이 이미지 없이 상단에 수행도를 n/n 으로 나타내는 프로그래스바
 */
@Composable
private fun DefaultProgressHeader(currentStep: Int, totalStep: Int) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.progress_mission),
            color = HistourTheme.colors.gray400,
            style = HistourTheme.typography.detail1Regular.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center
        )
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_flag),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(R.string.progress_step, currentStep, totalStep),
                color = HistourTheme.colors.gray400,
                style = HistourTheme.typography.detail1Regular.copy(fontWeight = FontWeight.Light),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 툴팁을 통해 진행도% 를 나타내는 위젯
 */
@Composable
private fun ProgressTooltip(progress: Float) {
    Box(
        modifier = Modifier
            .width(tooltipWidth.dp)
            .height(38.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tag_percent),
            contentDescription = "Progress Indicator",
            modifier = Modifier.fillMaxSize()
        )
        Text(
            modifier = Modifier.offset(y = (-5).dp),
            text = stringResource(
                R.string.progress_precent,
                (progress * 100).toInt().coerceIn(0, 100)
            ),
            color = HistourTheme.colors.white000,
            style = HistourTheme.typography.body2Reg.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * 진행도 끝에 위치 할 움직일 이미지
 */
@Composable
private fun ProgressImage(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Progress Indicator",
        modifier = Modifier
            .size(44.dp)
            .offset(y = 6.dp)
    )
}

private fun getProgressBarHeight(progressbarType: ProgressbarType): Int {
    return when (progressbarType) {
        ProgressbarType.DEFAULT -> progressDefaultHeight
        ProgressbarType.IMAGE -> progressWithImage
        ProgressbarType.TOOLTIP -> progressWithTooltipHeight
        ProgressbarType.SUBMISSION -> progressBarHeight
    }
}

private fun calculateProgress(
    progressbarType: ProgressbarType,
    currentStep: Int,
    totalStep: Int,
    progress: Float
): Float {
    return when (progressbarType) {
        ProgressbarType.DEFAULT -> runCatching { currentStep.toFloat() / totalStep.toFloat() }.getOrElse { progress }
        else -> progress
    }
}


@Composable
@Preview(device = Devices.PHONE)
private fun HistourProgressBarPhone() {
    HistourTheme {
        var currentStep by remember {
            mutableStateOf(0)
        }
        var progress by remember { mutableStateOf(0f) }
        var progressWithTooltip by remember { mutableStateOf(0f) }


        Column {
            Spacer(modifier = Modifier.size(16.dp))
            HistourProgressBar(
                histourProgressBarModel = HistourProgressBarModel(
                    totalStep = 7,
                ),
                progress = progress,
                currentStep = currentStep,
                progressbarType = ProgressbarType.DEFAULT

            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
            )

            // 버튼을 눌러 진행도 증가
            Button(onClick = { currentStep = (currentStep + 1).coerceAtMost(7) }) {
                Text("진행도 증가")
            }


            HistourProgressBar(
                histourProgressBarModel = HistourProgressBarModel(
                    totalStep = 5,
                ),
                progress = progress,
                progressbarType = ProgressbarType.IMAGE
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
            )

            // 버튼을 눌러 프로그래스 증가
            Button(onClick = { progress = (progress + 0.2f).coerceAtMost(1f) }) {
                Text("진행도 증가")
            }

            Spacer(modifier = Modifier.size(16.dp))

            HistourProgressBar(
                histourProgressBarModel = HistourProgressBarModel(
                    totalStep = 5,
                ),
                progress = progressWithTooltip,
                progressbarType = ProgressbarType.TOOLTIP
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
            )

            // 버튼을 눌러 프로그래스 증가
            Button(onClick = {
                progressWithTooltip = (progressWithTooltip + 0.2f).coerceAtMost(1f)
            }) {
                Text("진행도 증가")
            }

            HistourProgressBar(
                histourProgressBarModel = HistourProgressBarModel(
                    totalStep = 5,
                ),
                progress = progressWithTooltip,
                progressbarType = ProgressbarType.SUBMISSION
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
            )

            // 버튼을 눌러 프로그래스 증가
            Button(onClick = {
                progressWithTooltip = (progressWithTooltip + 0.2f).coerceAtMost(1f)
            }) {
                Text("진행도 증가")
            }

        }

    }
}
