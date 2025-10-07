package com.example.mining.data

import com.google.gson.annotations.SerializedName

// For location search
data class AccuWeatherLocation(
    @SerializedName("Key") val key: String,
    @SerializedName("LocalizedName") val localizedName: String,
    @SerializedName("Country") val country: Country,
    @SerializedName("GeoPosition") val geoPosition: GeoPosition
)