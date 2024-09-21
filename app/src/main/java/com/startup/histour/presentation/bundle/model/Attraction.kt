package com.startup.histour.presentation.bundle.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Attraction(
    @SerializedName("description")
    val description: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val name: String
) : Serializable {
    companion object {
        fun orEmpty(): Attraction = Attraction("", "", "")
    }
}
