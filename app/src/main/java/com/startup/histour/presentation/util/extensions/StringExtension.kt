package com.startup.histour.presentation.util.extensions

import android.graphics.Typeface
import android.text.Html
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

fun String.toAnnotatedString(): AnnotatedString {
    val text = replace("\n", "<br>")
    val spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    return buildAnnotatedString {
        append(spanned.toString())
        spanned.getSpans(0, spanned.length, Any::class.java)
            .forEach { span ->
                val start = spanned.getSpanStart(span)
                val end = spanned.getSpanEnd(span)
                when (span) {
                    is StyleSpan -> when (span.style) {
                        Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
                        Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
                        Typeface.BOLD_ITALIC -> SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                        )

                        else -> null
                    }

                    is StrikethroughSpan -> SpanStyle(textDecoration = TextDecoration.LineThrough)
                    is UnderlineSpan -> SpanStyle(textDecoration = TextDecoration.Underline)
                    is ForegroundColorSpan -> SpanStyle(color = Color(span.foregroundColor))
                    else -> null
                }?.let { style ->
                    addStyle(style, start, end)
                }
            }
    }
}
