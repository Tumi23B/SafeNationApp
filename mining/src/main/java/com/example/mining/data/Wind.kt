package com.example.mining.data

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("Direction") val direction: Direction,
    @SerializedName("Speed") val speed: Speed
)