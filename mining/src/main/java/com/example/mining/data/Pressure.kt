package com.example.mining.data

import com.google.gson.annotations.SerializedName

data class Pressure(
    @SerializedName("Metric") val metric: Metric,
    @SerializedName("Imperial") val imperial: Imperial
)
