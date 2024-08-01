package com.startup.histour.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.startup.histour.ui.theme.HistourTheme

@Composable
fun HistourSnackBar(snackBarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = remember { snackBarHostState },
    ) { snackBarData ->
        Column(
            modifier = Modifier.wrapContentWidth(),
        ) {
            Row(
                modifier = Modifier.background(
                    color = HistourTheme.colors.dim80,
                    shape = CircleShape,
                ),
            ) {
                Text(
                    snackBarData.visuals.message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    color = HistourTheme.colors.white000,
                    style = HistourTheme.typography.detail1Regular.copy(fontWeight = FontWeight.Light),
                )
            }
            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}