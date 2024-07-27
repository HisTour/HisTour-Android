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
fun CharacterChipText(@StringRes text: Int) {
    Text(
        style = HistourTheme.typography.detail1Bold.copy(color = HistourTheme.colors.white000),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .height(32.dp)
            .background(color = HistourTheme.colors.gray800, shape = CircleShape)
            .padding(horizontal = 17.dp, vertical = 7.dp),
        text = stringResource(text),
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewCharacterChipText() {
    HistourTheme {
        Column {
            CharacterChipText(R.string.character_name_1)
        }
    }
}