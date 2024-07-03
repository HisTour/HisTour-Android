package com.startup.histour.core.extension

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true

fun Byte?.orZero() = this ?: 0

fun Short?.orZero() = this ?: 0

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0L

fun Float?.orZero() = this ?: 0.0F

fun Double?.orZero() = this ?: 0.0

fun Char?.orBlank() = this ?: '\u0000'

fun String?.orBlank() = this ?: ""
