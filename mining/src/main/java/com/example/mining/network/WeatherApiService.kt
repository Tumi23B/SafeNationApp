package com.example.mining.network



import com.example.mining.data.AccuWeatherCurrentConditions
import com.example.mining.data.AccuWeatherLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {

    // AccuWeather APIs
    @GET("locations/v1/cities/geoposition/search")
    suspend fun getLocationKey(
        @Query("q") coordinates: String, // Format: "lat,lon"
        @Query("apikey") apiKey: String = "zpka_df253da20cb34648bcc77a82420347bf_1c12ec2e"
    ): Response<AccuWeatherLocation>

    @GET("currentconditions/v1/{locationKey}")
    suspend fun getCurrentConditions(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apiKey: String = "zpka_df253da20cb34648bcc77a82420347bf_1c12ec2e",
        @Query("details") details: Boolean = true
    ): Response<List<AccuWeatherCurrentConditions>>

    // Keep OpenWeatherMap as fallback
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = "b6907d289e10d714a6e88b30761fae22"
    ): Response<com.example.mining.data.WeatherResponse>
}