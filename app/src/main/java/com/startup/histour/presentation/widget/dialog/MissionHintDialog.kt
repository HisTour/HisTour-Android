package com.startup.histour.presentation.widget.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.startup.histour.R
import com.startup.histour.presentation.widget.text.CharacterSelectableChipText
import com.startup.histour.ui.theme.HistourTheme


enum class MissionDialogType {
    HINT,
    MISSION_CONTENT,
    ANSWER
}

enum class MissionType {
    PHOTO
    //TODO 추가
}

data class MissionContentDataModel(
    val missionTitle: String,
    val missionLevel: Int,
)

/**
데이터를 다이얼로그 내부에서 받을지 힌트 버튼 누르고 가져와서 다이얼로그에서 전달할지는 아직 모르지만
우선 구성
 */
@Composable
fun MissionHintDialog(
    dialogContent: String,
    onClickAnswer: () -> Unit,
    onClickClose: () -> Unit,
    missionDialogType: MissionDialogType = MissionDialogType.MISSION_CONTENT,
    missionContentData: MissionContentDataModel? = null
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxHeight = when (missionDialogType) {
        MissionDialogType.MISSION_CONTENT, MissionDialogType.ANSWER -> screenHeight * 0.6f
        MissionDialogType.HINT -> screenHeight * 0.8f
    }

    Dialog(
        onDismissRequest = { onClickClose() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            val x = (constraints.maxWidth * 0.4f).toInt() - placeable.width / 2
                            placeable.placeRelative(x = x, y = 0)
                        }
                    }
                    .clickable { onClickClose() },
                painter = painterResource(id = R.drawable.btn_close),
                contentDescription = "close",
            )

            when (missionDialogType) {
                MissionDialogType.MISSION_CONTENT -> {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .paint(
                                painter = painterResource(
                                    id = R.drawable.bg_paper_small,
                                ),
                                contentScale = ContentScale.FillBounds
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        MissionContent(
                            missionDescription = dialogContent,
                            missionContentData = missionContentData
                        )
                    }
                }

                MissionDialogType.HINT, MissionDialogType.ANSWER -> {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .paint(
                                painter = painterResource(
                                    id = R.drawable.bg_paper_large,
                                ),
                                contentScale = ContentScale.FillBounds
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        val title =
                            if (missionDialogType == MissionDialogType.ANSWER) stringResource(
                                id = R.string.dialog_answer
                            ) else {
                                stringResource(id = R.string.dialog_hint)
                            }

                        HintContent(title = title, content = dialogContent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            if (missionDialogType != MissionDialogType.ANSWER) {
                CharacterSelectableChipText(select = true, text = "정답 보기") {
                    onClickAnswer()
                }
            }

        }
    }
}

@Composable
private fun HintContent(title: String, content: String) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_hint),
            contentDescription = "hint",
            tint = Color.Unspecified,
        )
        Text(
            text = title,
            style = HistourTheme.typography.head3,
            color = HistourTheme.colors.gray900
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 60.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = content,
            style = HistourTheme.typography.body3Reg,
            color = HistourTheme.colors.gray700
        )
    }
}

@Composable
fun MissionContent(
    missionDescription: String,
    missionContentData: MissionContentDataModel?,
    missionType: MissionType = MissionType.PHOTO
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(23.dp))
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50.dp))
                .height(24.dp)
                .width(IntrinsicSize.Max)
                .background(HistourTheme.colors.gray100),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (missionType) {
                MissionType.PHOTO -> {
                    Icon(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_photo),
                        tint = Color.Unspecified,
                        contentDescription = "date"
                    )
                }
            }
            Text(
                modifier = Modifier.padding(end = 12.dp),
                text = "포토 미션",
                style = HistourTheme.typography.detail3Semi,
                color = HistourTheme.colors.gray400
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.height(30.dp),
                text = missionContentData?.missionTitle ?: "닭갈비 먹기",
                style = HistourTheme.typography.head3,
                color = HistourTheme.colors.gray900
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_level),
                    style = HistourTheme.typography.body3Bold,
                    color = HistourTheme.colors.green400
                )
                StarRating(level = missionContentData?.missionLevel ?: 1)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 60.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = missionDescription,
                style = HistourTheme.typography.detail1Regular,
                color = HistourTheme.colors.gray400
            )
        }
    }
}


@Composable
fun StarRating(level: Int, totalStars: Int = 5) {
    Row {
        repeat(totalStars) { index ->
            Image(
                painter = painterResource(
                    id = if (index < level) R.drawable.ic_star_green
                    else R.drawable.ic_star_gray
                ),
                contentDescription = "Star",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
@Preview(device = Devices.PHONE)
private fun HintDialogPreview() {
    val missionData = MissionContentDataModel(
        missionTitle = "닭갈비 먹기",
        missionLevel = 3,
    )

    HistourTheme {
//        MissionHintDialog(
//            dialogContent = "춘천에서 가장 유명한 닭갈비 집에서 닭갈비 먹고 인증샷을 남겨주세요. 맛있는 볶음밥은 필수!",
//            onClickAnswer = { /*TODO*/ },
//            onClickClose = { /*TODO*/ },
//            missionContentData = null,
//            missionDialogType = MissionDialogType.HINT
//        )
        MissionHintDialog(
            dialogContent = "춘천에서 가장 유명한 닭갈비 집에서 닭갈비 먹고 인증샷을 남겨주세요. 맛있는 볶음밥은 필수",
            onClickAnswer = { /*TODO*/ },
            onClickClose = { /*TODO*/ },
            missionContentData = missionData,
            missionDialogType = MissionDialogType.MISSION_CONTENT
        )
    }
}