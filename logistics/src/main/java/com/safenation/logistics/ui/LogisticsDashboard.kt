package com.safenation.logistics.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.material.card.MaterialCardView
import com.safenation.logistics.R
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class LogisticsDashboard : AppCompatActivity() {

    // Location services
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    // API services
    private lateinit var weatherApi: WeatherApi
    private lateinit var roadSafetyApi: RoadSafetyApi
    private lateinit var geocodingApi: GeocodingApi

    // UI elements for real-time data
    private lateinit var weatherCard: MaterialCardView
    private lateinit var safetyCard: MaterialCardView
    private lateinit var refreshButton: Button

    // Weather UI
    private lateinit var weatherTempText: TextView
    private lateinit var weatherLocationText: TextView
    private lateinit var weatherWindText: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherConditionText: TextView

    // Safety UI
    private lateinit var safetyStatusText: TextView
    private lateinit var safetyFeaturesText: TextView
    private lateinit var safetyDistanceText: TextView
    private lateinit var safetyIcon: ImageView

    // Current location and data
    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0
    private var currentLocationName: String = "Unknown Location"
    private var safetyFeatures: List<SafetyFeature> = emptyList()
    private var currentWeather: CurrentWeatherData? = null

    // Location permission launcher
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                startLocationUpdates()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                startLocationUpdates()
            }
            else -> {
                showLocationPermissionDenied()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_logistics_dashboard)

        // Initialize location services
        initializeLocationServices()

        // Initialize API clients
        initializeApis()

        // Handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard_screen)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize and bind views
        initializeViews()

        // Check location permission and fetch data
        checkLocationPermission()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun initializeLocationServices() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            TimeUnit.SECONDS.toMillis(10)
        ).apply {
            setMinUpdateIntervalMillis(TimeUnit.SECONDS.toMillis(5))
            setMaxUpdateDelayMillis(TimeUnit.SECONDS.toMillis(15))
        }.build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude

                    // Get location name and fetch data
                    getLocationName(currentLatitude, currentLongitude)
                    fetchWeatherData(currentLatitude, currentLongitude)
                    fetchRoadSafetyData(currentLatitude, currentLongitude)
                }
            }
        }
    }

    private fun initializeApis() {
        // Weather API setup
        val weatherRetrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = weatherRetrofit.create(WeatherApi::class.java)

        // Road Safety API setup
        val safetyRetrofit = Retrofit.Builder()
            .baseUrl("https://overpass-api.de/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        roadSafetyApi = safetyRetrofit.create(RoadSafetyApi::class.java)

        // Geocoding API for location names - using a different service
        val geocodingRetrofit = Retrofit.Builder()
            .baseUrl("https://api.bigdatacloud.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        geocodingApi = geocodingRetrofit.create(GeocodingApi::class.java)
    }

    private fun initializeViews() {
        // Existing sections
        val vehicleInspection = findViewById<LinearLayout?>(R.id.vehicleInspectionSection)
        val reportIssue = findViewById<LinearLayout?>(R.id.reportIssueSection)
        val healthFitness = findViewById<LinearLayout?>(R.id.healthFitnessSection)
        val drivingTips = findViewById<LinearLayout?>(R.id.drivingTipsSection)
        val checklist = findViewById<LinearLayout?>(R.id.checklistSection)
        val emergencyButton = findViewById<MaterialCardView?>(R.id.emergencyButtonCard)
        val settingsIcon = findViewById<ImageView?>(R.id.settingsIcon)

        // New real-time data views
        initializeRealTimeDataViews()

        // Set click listeners for existing features
        vehicleInspection?.setOnClickListener { navigateTo(VehicleInspection::class.java) }
        reportIssue?.setOnClickListener { navigateTo(ReportIssue::class.java) }
        healthFitness?.setOnClickListener { navigateTo(HealthFitness::class.java) }
        drivingTips?.setOnClickListener { navigateTo(DrivingTips::class.java) }
        checklist?.setOnClickListener { navigateTo(Checklist::class.java) }
        emergencyButton?.setOnClickListener { navigateTo(Emergency::class.java) }

        settingsIcon?.setOnClickListener {
            try {
                val intent = Intent(this, LogisticsSettings::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Unable to open Settings", Toast.LENGTH_SHORT).show()
            }
        }

        // Refresh button click listener
        refreshButton.setOnClickListener {
            forceLocationUpdate()
        }

        // Make cards clickable for detailed information
        weatherCard.setOnClickListener {
            showWeatherDetails()
        }

        safetyCard.setOnClickListener {
            showSafetyDetails()
        }
    }

    private fun initializeRealTimeDataViews() {
        // Weather card views
        weatherCard = findViewById(R.id.weatherCard)
        weatherTempText = findViewById(R.id.weatherTempText)
        weatherLocationText = findViewById(R.id.weatherLocationText)
        weatherWindText = findViewById(R.id.weatherWindText)
        weatherIcon = findViewById(R.id.weatherIcon)
        weatherConditionText = findViewById(R.id.weatherConditionText)

        // Safety card views
        safetyCard = findViewById(R.id.safetyCard)
        safetyStatusText = findViewById(R.id.safetyStatusText)
        safetyFeaturesText = findViewById(R.id.safetyFeaturesText)
        safetyDistanceText = findViewById(R.id.safetyDistanceText)
        safetyIcon = findViewById(R.id.safetyIcon)

        // Refresh button
        refreshButton = findViewById(R.id.refreshDataButton)

        // Set initial states
        weatherTempText.text = "--°C"
        weatherLocationText.text = "Getting location..."
        weatherWindText.text = "Wind: -- km/h"
        weatherConditionText.text = ""

        safetyStatusText.text = "Scanning..."
        safetyFeaturesText.text = "Safety features nearby"
        safetyDistanceText.text = "Tap for details"
    }

    private fun getLocationName(lat: Double, lng: Double) {
        lifecycleScope.launch {
            try {
                // Try multiple geocoding services if one fails
                val locationInfo = geocodingApi.reverseGeocode(lat, lng)

                runOnUiThread {
                    currentLocationName = when {
                        !locationInfo.city.isNullOrEmpty() -> locationInfo.city
                        !locationInfo.locality.isNullOrEmpty() -> locationInfo.locality
                        !locationInfo.principalSubdivision.isNullOrEmpty() -> locationInfo.principalSubdivision
                        else -> "Near ${String.format("%.2f, %.2f", lat, lng)}"
                    }

                    weatherLocationText.text = currentLocationName
                    Toast.makeText(this@LogisticsDashboard, "Location: $currentLocationName", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Fallback: Use coordinates with better formatting
                runOnUiThread {
                    currentLocationName = "Location: ${String.format("%.4f, %.4f", lat, lng)}"
                    weatherLocationText.text = currentLocationName
                    Toast.makeText(this@LogisticsDashboard, "Using GPS coordinates", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                startLocationUpdates()
            }
            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                    getLocationName(currentLatitude, currentLongitude)
                    fetchWeatherData(currentLatitude, currentLongitude)
                    fetchRoadSafetyData(currentLatitude, currentLongitude)
                } else {
                    weatherLocationText.text = "Getting fresh location..."
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                }
            }
            .addOnFailureListener { e ->
                weatherLocationText.text = "Starting location updates..."
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
    }

    private fun forceLocationUpdate() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkLocationPermission()
            return
        }

        weatherLocationText.text = "Refreshing location..."
        safetyStatusText.text = "Scanning..."
        Toast.makeText(this, "Updating location data...", Toast.LENGTH_SHORT).show()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun fetchWeatherData(lat: Double, lng: Double) {
        lifecycleScope.launch {
            try {
                val weather = weatherApi.getWeather(lat, lng)
                currentWeather = CurrentWeatherData(
                    temperature = weather.current_weather.temperature,
                    windspeed = weather.current_weather.windspeed,
                    weathercode = weather.current_weather.weathercode,
                    condition = getWeatherCondition(weather.current_weather.weathercode)
                )

                runOnUiThread {
                    weatherTempText.text = "${weather.current_weather.temperature}°C"
                    weatherWindText.text = "Wind: ${weather.current_weather.windspeed} km/h"
                    updateWeatherIcon(weather.current_weather.weathercode)
                    updateWeatherCondition(weather.current_weather.weathercode)
                    Toast.makeText(this@LogisticsDashboard, "Weather updated!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    weatherTempText.text = "--°C"
                    weatherWindText.text = "Weather unavailable"
                    weatherConditionText.text = ""
                    Toast.makeText(this@LogisticsDashboard, "Weather data error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchRoadSafetyData(lat: Double, lng: Double) {
        lifecycleScope.launch {
            try {
                // Simpler query that's more likely to work
                val query = """
                    [out:json][timeout:30];
                    (
                      node["amenity"="police"](around:5000,$lat,$lng);
                      node["amenity"="fuel"](around:5000,$lat,$lng);
                      node["amenity"="hospital"](around:5000,$lat,$lng);
                      node["highway"="speed_camera"](around:5000,$lat,$lng);
                    );
                    out;
                """.trimIndent()

                val response = roadSafetyApi.getSafetyFeatures(query)

                // Process safety features with better error handling
                safetyFeatures = response.elements.mapNotNull { element ->
                    try {
                        val featureType = when {
                            element.tags["amenity"] == "police" -> "🚓 Police Station"
                            element.tags["amenity"] == "fuel" -> "⛽ Fuel Station"
                            element.tags["amenity"] == "hospital" -> "🏥 Hospital"
                            element.tags["highway"] == "speed_camera" -> "🚨 Speed Camera"
                            else -> null
                        }

                        if (featureType != null && element.lat != null && element.lon != null) {
                            val name = element.tags["name"] ?: "Unnamed ${featureType.substringAfter(" ")}"

                            SafetyFeature(
                                type = featureType,
                                name = name,
                                latitude = element.lat,
                                longitude = element.lon,
                                distance = calculateDistance(lat, lng, element.lat, element.lon)
                            )
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        null
                    }
                }

                runOnUiThread {
                    val totalFeatures = safetyFeatures.size
                    safetyStatusText.text = when {
                        totalFeatures > 8 -> "🚨 High Alert"
                        totalFeatures > 4 -> "⚠️ Moderate Alert"
                        totalFeatures > 0 -> "✅ Clear Route"
                        else -> "🟢 No Alerts"
                    }

                    safetyFeaturesText.text = when {
                        totalFeatures == 0 -> "No safety features"
                        totalFeatures == 1 -> "1 safety feature"
                        else -> "$totalFeatures safety features"
                    }

                    safetyDistanceText.text = "Tap for details"
                    Toast.makeText(this@LogisticsDashboard, "Found $totalFeatures safety features", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    safetyStatusText.text = "Scan Failed"
                    safetyFeaturesText.text = "Check connection"
                    safetyDistanceText.text = "Try again"
                    Toast.makeText(this@LogisticsDashboard, "Safety data error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        val distanceInMeters = results[0]

        return when {
            distanceInMeters < 1000 -> "${distanceInMeters.toInt()}m away"
            else -> "${String.format("%.1f", distanceInMeters / 1000)}km away"
        }
    }

    private fun updateWeatherIcon(weatherCode: Int) {
        val iconRes = when (weatherCode) {
            in 0..1 -> android.R.drawable.presence_online // Clear
            in 2..3 -> android.R.drawable.presence_away // Partly cloudy
            in 45..48 -> android.R.drawable.presence_invisible // Fog
            in 51..67 -> android.R.drawable.presence_busy // Rain
            in 71..86 -> android.R.drawable.star_big_on // Snow
            in 95..99 -> android.R.drawable.stat_sys_warning // Thunderstorm
            else -> android.R.drawable.stat_sys_warning // Unknown
        }
        weatherIcon.setImageResource(iconRes)
    }

    private fun updateWeatherCondition(weatherCode: Int) {
        val condition = getWeatherCondition(weatherCode)
        weatherConditionText.text = condition
    }

    private fun getWeatherCondition(weatherCode: Int): String {
        return when (weatherCode) {
            in 0..1 -> "Clear sky"
            in 2..3 -> "Partly cloudy"
            in 45..48 -> "Foggy"
            in 51..55 -> "Light rain"
            in 56..57 -> "Freezing drizzle"
            in 61..65 -> "Rainy"
            in 66..67 -> "Freezing rain"
            in 71..75 -> "Light snow"
            in 77..77 -> "Snow grains"
            in 80..82 -> "Rain showers"
            in 85..86 -> "Snow showers"
            in 95..99 -> "Thunderstorm"
            else -> "Unknown weather"
        }
    }

    private fun showWeatherDetails() {
        val weather = currentWeather
        if (weather == null) {
            Toast.makeText(this, "Weather data not available", Toast.LENGTH_SHORT).show()
            return
        }

        val weatherDetails = """
            🌤️ WEATHER DETAILS
            
            📍 Location: $currentLocationName
            🌡️ Temperature: ${weather.temperature}°C
            🌬️ Wind Speed: ${weather.windspeed} km/h
            ☁️ Condition: ${weather.condition}
            
            📊 Coordinates: 
            Latitude: ${String.format("%.4f", currentLatitude)}
            Longitude: ${String.format("%.4f", currentLongitude)}
            
            💡 Driving Advice: ${getDrivingAdvice(weather.weathercode)}
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Current Weather")
            .setMessage(weatherDetails)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNeutralButton("Refresh") { dialog, _ ->
                forceLocationUpdate()
                dialog.dismiss()
            }
            .show()
    }

    private fun showSafetyDetails() {
        if (safetyFeatures.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Safety Features")
                .setMessage("No safety features found within 5km radius.\n\nThis could be because:\n• You're in a remote area\n• No safety data available\n• Poor internet connection\n\nTry moving to a different location or check your connection.")
                .setPositiveButton("OK", null)
                .setNeutralButton("Refresh") { dialog, _ ->
                    forceLocationUpdate()
                    dialog.dismiss()
                }
                .show()
            return
        }

        val featuresText = safetyFeatures.take(10).joinToString("\n\n") { feature ->
            "${feature.type}\n" +
                    "🏷️ ${feature.name}\n" +
                    "📏 ${feature.distance}"
        }

        val totalFeatures = safetyFeatures.size
        val alertLevel = when {
            totalFeatures > 8 -> "🚨 HIGH ALERT AREA"
            totalFeatures > 4 -> "⚠️ MODERATE ALERT AREA"
            else -> "✅ CLEAR ROUTE"
        }

        val details = """
            $alertLevel
            
            Found $totalFeatures safety features within 5km:
            
            $featuresText
            ${if (totalFeatures > 10) "\n...and ${totalFeatures - 10} more features" else ""}
            
            💡 Stay alert and drive safely!
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Road Safety Information")
            .setMessage(details)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNeutralButton("Refresh") { dialog, _ ->
                forceLocationUpdate()
                dialog.dismiss()
            }
            .show()
    }

    private fun getDrivingAdvice(weatherCode: Int): String {
        return when (weatherCode) {
            in 0..1 -> "Clear conditions - Good for driving"
            in 2..3 -> "Partly cloudy - Normal driving conditions"
            in 45..48 -> "Foggy - Reduce speed, use fog lights"
            in 51..67 -> "Rainy - Increase following distance"
            in 71..86 -> "Snowy - Drive carefully, avoid sudden moves"
            in 95..99 -> "Thunderstorm - Consider stopping if severe"
            else -> "Drive with caution"
        }
    }

    private fun showLocationPermissionDenied() {
        AlertDialog.Builder(this)
            .setTitle("Location Permission Required")
            .setMessage("This app needs location access to provide:\n\n• Real-time weather updates\n• Nearby safety features\n• Route safety information\n\nPlease enable location permissions in settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = android.net.Uri.fromParts("package", packageName, null)
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()

        weatherLocationText.text = "Permission needed"
        safetyStatusText.text = "Enable location"
    }

    private fun navigateTo(activityClass: Class<*>) {
        try {
            val intent = Intent(this, activityClass)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open this feature", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logMissingView(viewName: String) {
        println("⚠️ Warning: View with ID $viewName not found in layout")
    }
}

// Data Classes
data class SafetyFeature(
    val type: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val distance: String
)

data class CurrentWeatherData(
    val temperature: Double,
    val windspeed: Double,
    val weathercode: Int,
    val condition: String
)

// API Interfaces
interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("current_weather") current: Boolean = true
    ): WeatherResponse
}

interface RoadSafetyApi {
    @GET("interpreter")
    suspend fun getSafetyFeatures(
        @Query("data") query: String
    ): OverpassResponse
}

// Using BigDataCloud for better geocoding
interface GeocodingApi {
    @GET("data/reverse-geocode-client")
    suspend fun reverseGeocode(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("localityLanguage") language: String = "en"
    ): BigDataCloudResponse
}

// Response Data Classes
data class WeatherResponse(
    val current_weather: CurrentWeather
) {
    data class CurrentWeather(
        val temperature: Double,
        val windspeed: Double,
        val weathercode: Int
    )
}

data class OverpassResponse(
    val elements: List<OverpassElement>
)

data class OverpassElement(
    val type: String,
    val id: Long,
    val lat: Double?,
    val lon: Double?,
    val tags: Map<String, String> = emptyMap()
)

// BigDataCloud Response
data class BigDataCloudResponse(
    val locality: String?,
    val city: String?,
    val principalSubdivision: String?,
    val countryName: String?
)