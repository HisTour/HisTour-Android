package com.startup.histour.presentation.bundle.model

import java.io.Serializable

data class Attraction(
    val description: String,
    val imageUrl: String,
    val name: String
): Serializable
