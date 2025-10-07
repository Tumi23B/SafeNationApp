package com.example.mining.data

import com.google.gson.annotations.SerializedName

data class RealFeelTemperature(
    @SerializedName("Metric") val metric: Metric
)
