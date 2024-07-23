package com.startup.histour.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.errorprone.annotations.Immutable


// Main
val Green200 = Color(0xFFBEF65C)
val Green400 = Color(0xFF00DE4D)

// Sub
val Yellow100 = Color(0xFFFFF8D5)
val Yellow200 = Color(0xFFFFF4B8)
val Yellow300 = Color(0xFFFFE454)
val Yellow700 = Color(0xFFBC7C00)

// Sementic
val Blue300 = Color(0xFF73A8FF)
val Red300 = Color(0xFFFF7474)

// GrayScale
val Gray10 = Color(0xFFF9FAFB)
val Gray20 = Color(0xFFF3F4F6)
val Gray30 = Color(0xFFE2E3E5)
val Gray40 = Color(0xFFD4D6D9)
val Gray50 = Color(0xFFA4A7AC)
val Gray60 = Color(0xFF7B7D85)
val Gray70 = Color(0xFF5A5F67)
val Gray80 = Color(0xFF474C55)
val Gray90 = Color(0xFF2C3138)
val Gray100 = Color(0xFF1C2028)
val White = Color(0xFFFFFFFF)


@Immutable
data class HistourSemanticColor(
    val green200: Color,
    val green400: Color,
    val yellow100: Color,
    val yellow200: Color,
    val yellow300: Color,
    val yellow700: Color,
    val blue300: Color,
    val red300: Color,
    val gray10: Color,
    val gray20: Color,
    val gray30: Color,
    val gray40: Color,
    val gray50: Color,
    val gray60: Color,
    val gray70: Color,
    val gray80: Color,
    val gray90: Color,
    val gray100: Color,
    val white: Color
)

val LocalHistourColors = staticCompositionLocalOf {
    HistourSemanticColor(
        green200 = Color.Unspecified,
        green400 = Color.Unspecified,
        yellow100 = Color.Unspecified,
        yellow200 = Color.Unspecified,
        yellow300 = Color.Unspecified,
        yellow700 = Color.Unspecified,
        blue300 = Color.Unspecified,
        red300 = Color.Unspecified,
        gray10 = Color.Unspecified,
        gray20 = Color.Unspecified,
        gray30 = Color.Unspecified,
        gray40 = Color.Unspecified,
        gray50 = Color.Unspecified,
        gray60 = Color.Unspecified,
        gray70 = Color.Unspecified,
        gray80 = Color.Unspecified,
        gray90 = Color.Unspecified,
        gray100 = Color.Unspecified,
        white = Color.Unspecified,
    )
}
val LocalHistourLightColorScheme = HistourSemanticColor(
    green200 = Green200,
    green400 = Green400,
    yellow100 = Yellow100,
    yellow200 = Yellow200,
    yellow300 = Yellow300,
    yellow700 = Yellow700,
    blue300 = Blue300,
    red300 = Red300,
    gray10 = Gray10,
    gray20 = Gray20,
    gray30 = Gray30,
    gray40 = Gray40,
    gray50 = Gray50,
    gray60 = Gray60,
    gray70 = Gray70,
    gray80 = Gray80,
    gray90 = Gray90,
    gray100 = Gray100,
    white = White,
)

val LocalHistourDarkColorScheme = HistourSemanticColor(
    green200 = Green200,
    green400 = Green400,
    yellow100 = Yellow100,
    yellow200 = Yellow200,
    yellow300 = Yellow300,
    yellow700 = Yellow700,
    blue300 = Blue300,
    red300 = Red300,
    gray10 = Gray10,
    gray20 = Gray20,
    gray30 = Gray30,
    gray40 = Gray40,
    gray50 = Gray50,
    gray60 = Gray60,
    gray70 = Gray70,
    gray80 = Gray80,
    gray90 = Gray90,
    gray100 = Gray100,
    white = White,
)
