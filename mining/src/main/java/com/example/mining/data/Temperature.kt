package com.example.mining.data

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("Metric") val metric: Metric,
    @SerializedName("Imperial") val imperial: Imperial
)
