package com.example.mining.data

import com.google.gson.annotations.SerializedName


data class GeoPosition(
    @SerializedName("Latitude") val latitude: Double,
    @SerializedName("Longitude") val longitude: Double
)

