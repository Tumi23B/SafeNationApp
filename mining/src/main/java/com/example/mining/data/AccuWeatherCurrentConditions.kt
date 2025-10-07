package com.example.mining.data



import com.google.gson.annotations.SerializedName

data class AccuWeatherCurrentConditions(
    @SerializedName("LocalObservationDateTime") val localObservationDateTime: String,
    @SerializedName("EpochTime") val epochTime: Long,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType") val precipitationType: String?,
    @SerializedName("IsDayTime") val isDayTime: Boolean,
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("RealFeelTemperature") val realFeelTemperature: RealFeelTemperature,
    @SerializedName("RelativeHumidity") val relativeHumidity: Int,
    @SerializedName("Wind") val wind: Wind,
    @SerializedName("UVIndex") val uvIndex: Int,
    @SerializedName("UVIndexText") val uvIndexText: String,
    @SerializedName("Visibility") val visibility: Visibility,
    @SerializedName("Pressure") val pressure: Pressure
)






