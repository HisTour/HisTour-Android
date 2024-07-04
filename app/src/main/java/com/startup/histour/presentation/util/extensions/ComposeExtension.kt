package com.startup.histour.presentation.util.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun Dp.toSp() = with(LocalDensity.current) { this@toSp.toSp() }

@Composable
fun Int.spToDp() = with(LocalDensity.current) { this@spToDp.toDp() }

@Composable
fun Color.applyAlpha(isPressed: Boolean, alpha: Float = 0.2f) =
    this.copy(alpha = if (isPressed) alpha else 1f)

@Composable
fun Modifier.rippleClickable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit,
) =
    composed {
        this.clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(bounded = false),
            enabled = enabled,
            role = role,
            onClick = onClick,
        )
    }

@Composable
fun Modifier.noRippleClickable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit,
) = this.clickable(
    interactionSource = interactionSource,
    indication = null,
    enabled = enabled,
    role = role,
    onClick = onClick,
)
