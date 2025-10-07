package com.example.mining.data

import com.google.gson.annotations.SerializedName

data class Direction(
    @SerializedName("Degrees") val degrees: Int,
    @SerializedName("Localized") val localized: String,
    @SerializedName("English") val english: String
)
