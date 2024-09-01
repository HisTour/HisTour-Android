package com.startup.histour.presentation.widget.topbar

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.startup.histour.R

class TopBarPreviewProvider : PreviewParameterProvider<HistourTopBarModel> {
    override val values = sequenceOf(
        HistourTopBarModel(
            leftSectionType = HistourTopBarModel.LeftSectionType.Empty,
            rightSectionType = HistourTopBarModel.RightSectionType.Empty,
            titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character),
        ),
        HistourTopBarModel(
            leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                leftIcons = listOf(HistourTopBarModel.TopBarIcon.BACK),
                onClickLeftIcon = {},
            ),
            rightSectionType = HistourTopBarModel.RightSectionType.Empty,
            titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character),
        ),
        HistourTopBarModel(
            leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                listOf(HistourTopBarModel.TopBarIcon.BACK),
                onClickLeftIcon = {},
            ),
            rightSectionType = HistourTopBarModel.RightSectionType.Text(
                stringResId = R.string.title_character,
                state = HistourTopBarModel.RightSectionType.Text.State.INACTIVE,
                onClickRightTextArea = {},
            ),
            titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character),
        ),
        HistourTopBarModel(
            leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                listOf(HistourTopBarModel.TopBarIcon.BACK),
                onClickLeftIcon = {},
            ),
            rightSectionType = HistourTopBarModel.RightSectionType.Text(
                stringResId = R.string.save,
                state = HistourTopBarModel.RightSectionType.Text.State.SAVE,
                onClickRightTextArea = {},
            ),
            titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character),
        ),
        HistourTopBarModel(
            leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                listOf(HistourTopBarModel.TopBarIcon.BACK),
                onClickLeftIcon = {},
            ),
            rightSectionType = HistourTopBarModel.RightSectionType.Text(
                stringResId = R.string.finish,
                state = HistourTopBarModel.RightSectionType.Text.State.FINISH,
                onClickRightTextArea = {},
            ),
            titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character),
        ),
        HistourTopBarModel(
            leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                listOf(HistourTopBarModel.TopBarIcon.BACK),
                onClickLeftIcon = {},
            ),
            rightSectionType = HistourTopBarModel.RightSectionType.Text(
                stringResId = R.string.complete,
                state = HistourTopBarModel.RightSectionType.Text.State.COMPLETE,
                onClickRightTextArea = {},
            ),
            titleStyle = HistourTopBarModel.TitleStyle.Text(R.string.title_character),
        ),
        HistourTopBarModel(
            leftSectionType = HistourTopBarModel.LeftSectionType.Icons(
                listOf(HistourTopBarModel.TopBarIcon.BACK),
                onClickLeftIcon = {},
            ),
            rightSectionType = HistourTopBarModel.RightSectionType.Icons(
                listOf(HistourTopBarModel.TopBarIcon.BACK),
                onClickRightIcon = {},
            ),
            titleStyle = HistourTopBarModel.TitleStyle.TextWithIcon("강원도 춘천시", R.drawable.ic_launcher_foreground),
        ),
    )
}
