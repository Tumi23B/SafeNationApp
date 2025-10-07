package com.example.mining.data

import com.google.gson.annotations.SerializedName

data class Imperial(
    @SerializedName("Value") val value: Double,
    @SerializedName("Unit") val unit: String,
    @SerializedName("UnitType") val unitType: Int
)

