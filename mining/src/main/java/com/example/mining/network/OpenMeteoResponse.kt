package com.example.mining.network

import com.example.mining.network.CurrentWeather
import com.google.gson.annotations.SerializedName

data class OpenMeteoResponse(
    @SerializedName("current") val current: CurrentWeather,
    @SerializedName("timezone") val timezone: String
)