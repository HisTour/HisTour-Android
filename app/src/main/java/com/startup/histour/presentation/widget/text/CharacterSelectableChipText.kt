package com.startup.histour.presentation.widget.text

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.startup.histour.R
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun CharacterSelectableChipText(select: Boolean, @StringRes text: Int) {
    val selectedTextStyle = HistourTheme.typography.detail1Bold.copy(color = HistourTheme.colors.white000)
    val unSelectedTextStyle = HistourTheme.typography.detail1Bold.copy(color = HistourTheme.colors.gray500)
    val selectedBackgroundColor = HistourTheme.colors.green400
    val unSelectedBackgroundColor = HistourTheme.colors.gray200
    Text(
        style = if (select) selectedTextStyle else unSelectedTextStyle,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .height(32.dp)
            .background(color = if (select) selectedBackgroundColor else unSelectedBackgroundColor, shape = CircleShape)
            .padding(horizontal = 17.dp, vertical = 7.dp),
        text = stringResource(text),
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewCharacterSelectableChipText() {
    HistourTheme {
        Column {
            CharacterSelectableChipText(false, R.string.character_name_1)
            CharacterSelectableChipText(true, R.string.character_name_1)
        }
    }
}