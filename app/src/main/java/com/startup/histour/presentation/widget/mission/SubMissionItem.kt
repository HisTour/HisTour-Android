package com.startup.histour.presentation.widget.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.startup.histour.R
import com.startup.histour.presentation.util.extensions.noRippleClickable
import com.startup.histour.presentation.widget.progressbar.HistourProgressBar
import com.startup.histour.presentation.widget.progressbar.HistourProgressBarModel
import com.startup.histour.presentation.widget.progressbar.ProgressbarType
import com.startup.histour.ui.theme.HistourTheme

enum class SUBMISSIONSTATE {
    BEFORE,
    PROGRESS,
    COMPLETE
}

data class ProgressingTaskDataModel(
    val totalMissions: Int,
    val completedMissions: Int
)

@Composable
fun SubMissionItem(
    subMissionTitle: String,
    state: SUBMISSIONSTATE = SUBMISSIONSTATE.BEFORE,
    data: ProgressingTaskDataModel? = null,
    onChangeClick: (() -> Unit)? = null,
    onContinueClick: (() -> Unit)? = null
) {
    val strokeColor = when (state) {
        SUBMISSIONSTATE.COMPLETE -> HistourTheme.colors.green400
        SUBMISSIONSTATE.PROGRESS -> Color.Transparent
        SUBMISSIONSTATE.BEFORE -> HistourTheme.colors.gray200
    }

    val backgroundColor = when (state) {
        SUBMISSIONSTATE.COMPLETE -> HistourTheme.colors.green200
        SUBMISSIONSTATE.PROGRESS -> HistourTheme.colors.green400
        SUBMISSIONSTATE.BEFORE -> HistourTheme.colors.white000
    }

    val textColor = when (state) {
        SUBMISSIONSTATE.COMPLETE -> HistourTheme.colors.gray800
        SUBMISSIONSTATE.PROGRESS -> HistourTheme.colors.white000
        SUBMISSIONSTATE.BEFORE -> HistourTheme.colors.gray200
    }

    val height = when(state){
        SUBMISSIONSTATE.PROGRESS -> 166.dp
        else -> 140.dp
    }

    val width = when(state){
        SUBMISSIONSTATE.PROGRESS -> 260.dp
        else -> 240.dp
    }

    Card(
        modifier = Modifier
            .width(width)
            .height(height)
            .border(1.dp, strokeColor, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        when (state) {
            SUBMISSIONSTATE.BEFORE -> BeforeSubMission(
                submissionTitle = subMissionTitle,
                textColor = textColor
            )

            SUBMISSIONSTATE.PROGRESS -> ProgressSubMission(
                submissionTitle = subMissionTitle,
                textColor = textColor,
                data = data,
                onChangeClick,
                onContinueClick
            )

            SUBMISSIONSTATE.COMPLETE -> CompleteSubMission(
                submissionTitle = subMissionTitle,
                textColor = textColor,
            )
        }
    }
}

@Composable
fun BeforeSubMission(submissionTitle: String, textColor: Color) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .width(120.dp)
                .padding(top = 16.dp, start = 18.dp),
            text = submissionTitle,
            style = HistourTheme.typography.body1Bold,
            color = textColor,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(1f),
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = "Locked",
            tint = HistourTheme.colors.gray300
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            modifier = Modifier
                .width(108.dp)
                .height(95.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.img_submission_character),
            contentDescription = "Character",
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }),
        )
    }
}

@Composable
fun ProgressSubMission(
    submissionTitle: String,
    textColor: Color,
    data: ProgressingTaskDataModel?,
    onChangeClick: (() -> Unit)?,
    onContinueClick: (() -> Unit)?
) {

    val progress by remember { mutableStateOf(0f) }
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .width(130.dp)
                    .padding(top = 20.dp),
                text = submissionTitle,
                style = HistourTheme.typography.head4,
                color = textColor,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(id = R.drawable.img_submission_character),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Character",
                modifier = Modifier
                    .width(108.dp)
                    .height(95.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            HistourProgressBar(
                histourProgressBarModel = HistourProgressBarModel(
                    totalStep = data?.totalMissions ?: 5
                ),
                currentStep = data?.completedMissions ?: 0,
                progress = progress,
                progressbarType = ProgressbarType.SUBMISSION
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Column(
                    modifier = Modifier
                        .height(36.dp)
                        .width(110.dp)
                        .background(color = HistourTheme.colors.white000, shape = CircleShape)
                        .padding(horizontal = 14.dp)
                        .noRippleClickable { onChangeClick?.invoke() },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        style = HistourTheme.typography.body2Bold,
                        color = HistourTheme.colors.gray500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = stringResource(id = R.string.submission_change),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier
                        .height(36.dp)
                        .width(110.dp)
                        .background(color = HistourTheme.colors.gray900, shape = CircleShape)
                        .padding(horizontal = 17.dp)
                        .noRippleClickable { onContinueClick?.invoke() },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        style = HistourTheme.typography.body2Bold,
                        color = HistourTheme.colors.white000,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = stringResource(id = R.string.submission_continue),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

}

@Composable
fun CompleteSubMission(submissionTitle: String, textColor: Color) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .width(120.dp)
                .padding(top = 16.dp, start = 18.dp)
                .zIndex(1f),
            text = submissionTitle,
            style = HistourTheme.typography.body1Bold,
            color = textColor,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .width(124.dp)
                .height(90.dp),
            painter = painterResource(id = R.drawable.img_stamp),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Clear",
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.img_submission_character),
            contentDescription = "Character",
            modifier = Modifier
                .width(108.dp)
                .height(95.dp)
                .align(Alignment.BottomEnd)
        )
    }
}


@Composable
@Preview(device = Devices.PHONE)
private fun SubMissionItemPreview() {
    val subMissionData = ProgressingTaskDataModel(
        completedMissions = 1,
        totalMissions = 5
    )

    HistourTheme {
        Column {
            Spacer(modifier = Modifier.height(12.dp))
            SubMissionItem(subMissionTitle = "수원화성에서 사진찍기", state = SUBMISSIONSTATE.BEFORE)
            Spacer(modifier = Modifier.height(12.dp))
            SubMissionItem(
                subMissionTitle = "글자영역이 168이상 넘어가면 어떻게 되어야 할까요??????",
                state = SUBMISSIONSTATE.PROGRESS,
                data = subMissionData
            )
            Spacer(modifier = Modifier.height(12.dp))
            SubMissionItem(subMissionTitle = "수원화성에서 사진찍기", state = SUBMISSIONSTATE.COMPLETE)
        }
    }
}