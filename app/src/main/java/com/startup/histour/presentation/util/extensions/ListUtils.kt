package com.startup.histour.presentation.util.extensions

import kotlinx.collections.immutable.toImmutableList

fun <T> emptyImmutableList() =
    emptyList<T>().toImmutableList()

fun <T> List<T>?.orEmpty() =
    takeIf { it.isNullOrEmpty().not() }
        ?.toImmutableList()
        ?: emptyImmutableList()
