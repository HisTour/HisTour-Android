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
val Blue100 = Color(0xFFE3EEFF)
val Blue300 = Color(0xFF73A8FF)
val Red300 = Color(0xFFFF7474)

// GrayScale
val Gray50 = Color(0xFFF9FAFB)
val Gray100 = Color(0xFFF3F4F6)
val Gray200 = Color(0xFFE2E3E5)
val Gray300 = Color(0xFFD4D6D9)
val Gray400 = Color(0xFFA4A7AC)
val Gray500 = Color(0xFF7B7D85)
val Gray600 = Color(0xFF5A5F67)
val Gray700 = Color(0xFF474C55)
val Gray800 = Color(0xFF2C3138)
val Gray900 = Color(0xFF1C2028)
val Dim80 = Color(0xCC000000)
val White000 = Color(0xFFFFFFFF)


@Immutable
data class HistourSemanticColor(
    val green200: Color,
    val green400: Color,
    val yellow100: Color,
    val yellow200: Color,
    val yellow300: Color,
    val yellow700: Color,
    val blue100: Color,
    val blue300: Color,
    val red300: Color,
    val gray50: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val gray900: Color,
    val dim80: Color,
    val white000: Color
)

val LocalHistourColors = staticCompositionLocalOf {
    HistourSemanticColor(
        green200 = Color.Unspecified,
        green400 = Color.Unspecified,
        yellow100 = Color.Unspecified,
        yellow200 = Color.Unspecified,
        yellow300 = Color.Unspecified,
        yellow700 = Color.Unspecified,
        blue100 = Color.Unspecified,
        blue300 = Color.Unspecified,
        red300 = Color.Unspecified,
        gray50 = Color.Unspecified,
        gray100 = Color.Unspecified,
        gray200 = Color.Unspecified,
        gray300 = Color.Unspecified,
        gray400 = Color.Unspecified,
        gray500 = Color.Unspecified,
        gray600 = Color.Unspecified,
        gray700 = Color.Unspecified,
        gray800 = Color.Unspecified,
        gray900 = Color.Unspecified,
        dim80 = Color.Unspecified,
        white000 = Color.Unspecified,
    )
}
val LocalHistourLightColorScheme = HistourSemanticColor(
    green200 = Green200,
    green400 = Green400,
    yellow100 = Yellow100,
    yellow200 = Yellow200,
    yellow300 = Yellow300,
    yellow700 = Yellow700,
    blue100 = Blue100,
    blue300 = Blue300,
    red300 = Red300,
    gray50 = Gray50,
    gray100 = Gray100,
    gray200 = Gray200,
    gray300 = Gray300,
    gray400 = Gray400,
    gray500 = Gray500,
    gray600 = Gray600,
    gray700 = Gray700,
    gray800 = Gray800,
    gray900 = Gray900,
    dim80 = Dim80,
    white000 = White000,
)

val LocalHistourDarkColorScheme = HistourSemanticColor(
    green200 = Green200,
    green400 = Green400,
    yellow100 = Yellow100,
    yellow200 = Yellow200,
    yellow300 = Yellow300,
    yellow700 = Yellow700,
    blue100 = Blue100,
    blue300 = Blue300,
    red300 = Red300,
    gray50 = Gray50,
    gray100 = Gray100,
    gray200 = Gray200,
    gray300 = Gray300,
    gray400 = Gray400,
    gray500 = Gray500,
    gray600 = Gray600,
    gray700 = Gray700,
    gray800 = Gray800,
    gray900 = Gray900,
    dim80 = Dim80,
    white000 = White000,
)
