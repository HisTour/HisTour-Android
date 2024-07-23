package com.startup.histour.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object HistourTheme {

    val colors: HistourSemanticColor
        @Composable
        @ReadOnlyComposable
        get() = LocalHistourColors.current

    val typography: HistourTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHistourTypography.current
}

@Composable
fun HistourTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalHistourColors provides if (darkTheme) LocalHistourDarkColorScheme else LocalHistourLightColorScheme,
        LocalHistourTypography provides getHistourTypography(),
    ) {
        MaterialTheme(content = content)
    }
}
