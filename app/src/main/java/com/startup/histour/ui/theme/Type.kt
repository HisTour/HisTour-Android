package com.startup.histour.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.startup.histour.presentation.util.extensions.toSp

@Immutable
data class HistourTypography(
    val head1: TextStyle,
    val head2: TextStyle,
    val head3: TextStyle,
    val head4: TextStyle,
    val body1Bold: TextStyle,
    val body1Reg: TextStyle,
    val body2Bold: TextStyle,
    val body2Reg: TextStyle,
    val body3Bold: TextStyle,
    val body3Medi: TextStyle,
    val body3Reg: TextStyle,
    val detail1Bold: TextStyle,
    val detail1Regular: TextStyle,
    val detail2Bold: TextStyle,
    val detail2Regular: TextStyle,
    val detail2Semi: TextStyle,
    val detail3Semi: TextStyle,
    val detail3Regular: TextStyle,
)

val LocalHistourTypography = staticCompositionLocalOf {
    HistourTypography(
        head1 = TextStyle.Default,
        head2 = TextStyle.Default,
        head3 = TextStyle.Default,
        head4 = TextStyle.Default,
        body1Bold = TextStyle.Default,
        body1Reg = TextStyle.Default,
        body2Bold = TextStyle.Default,
        body2Reg = TextStyle.Default,
        body3Bold = TextStyle.Default,
        body3Medi = TextStyle.Default,
        body3Reg = TextStyle.Default,
        detail1Bold = TextStyle.Default,
        detail1Regular = TextStyle.Default,
        detail2Bold = TextStyle.Default,
        detail2Semi = TextStyle.Default,
        detail2Regular = TextStyle.Default,
        detail3Semi = TextStyle.Default,
        detail3Regular = TextStyle.Default,
    )
}

@Composable
fun getHistourTypography(): HistourTypography =
    HistourTypography(
        head1 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.dp.toSp(),
            lineHeight = 36.dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        head2 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 22.dp.toSp(),
            lineHeight = 33.dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        head3 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.dp.toSp(),
            lineHeight = 30.dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        head4 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 18.dp.toSp(),
            lineHeight = 27.dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        body1Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 16.dp.toSp(),
            lineHeight = (22.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        body1Reg = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 16.dp.toSp(),
            lineHeight = (16 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        body2Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 15.dp.toSp(),
            lineHeight = (15 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        body2Reg = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 15.dp.toSp(),
            lineHeight = (15 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        body3Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 14.dp.toSp(),
            lineHeight = (14 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        body3Medi = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.dp.toSp(),
            lineHeight = (14 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        body3Reg = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 14.dp.toSp(),
            lineHeight = (14 * 1.8).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        detail1Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 13.dp.toSp(),
            lineHeight = (13 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        detail1Regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 13.dp.toSp(),
            lineHeight = (13 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        detail2Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 12.dp.toSp(),
            lineHeight = (12 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        detail2Semi = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.dp.toSp(),
            lineHeight = (12 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        detail2Regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 12.dp.toSp(),
            lineHeight = (12 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        detail3Semi = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.dp.toSp(),
            lineHeight = (11 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
        detail3Regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 11.dp.toSp(),
            lineHeight = (11 * 1.4).dp.toSp(),
            platformStyle = PlatformTextStyle(includeFontPadding = false),
        ),
    )
