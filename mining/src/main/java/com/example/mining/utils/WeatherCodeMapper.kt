package com.example.mining.utils


object WeatherCodeMapper {

    fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Drizzle"
            56, 57 -> "Freezing drizzle"
            61, 63, 65 -> "Rain"
            66, 67 -> "Freezing rain"
            71, 73, 75 -> "Snow"
            77 -> "Snow grains"
            80, 81, 82 -> "Rain showers"
            85, 86 -> "Snow showers"
            95 -> "Thunderstorm"
            96, 99 -> "Thunderstorm with hail"
            else -> "Unknown"
        }
    }

    fun getWeatherIcon(code: Int): String {
        return when (code) {
            0 -> "☀️"  // Clear sky
            1, 2 -> "⛅"  // Mainly clear, partly cloudy
            3 -> "☁️"  // Overcast
            45, 48 -> "🌫️"  // Fog
            51, 53, 55, 56, 57 -> "🌧️"  // Drizzle
            61, 63, 65, 66, 67, 80, 81, 82 -> "🌧️"  // Rain
            71, 73, 75, 77, 85, 86 -> "❄️"  // Snow
            95, 96, 99 -> "⛈️"  // Thunderstorm
            else -> "🌈"
        }
    }
}