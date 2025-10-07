package com.safenation.mining.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.mining.network.RetrofitInstance
import com.example.mining.utils.WeatherCodeMapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.safenation.mining.R
import com.safenation.mining.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class HomeFragment : Fragment() {
        private var _binding: FragmentHomeBinding? = null
        private val binding get() = _binding!!

        private lateinit var fusedLocationClient: FusedLocationProviderClient
        private val LOCATION_PERMISSION_REQUEST_CODE = 1001

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
        }

        @SuppressLint("SetTextI18n", "MissingPermission")
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            // Set current date
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            binding.tvDate.text = "Today: $currentDate"

            setupQuickActions()

            binding.btnViewAllTasks.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_tasksFragment)
            }

            // Get weather data (NO API KEY NEEDED!)
            getCurrentLocationAndWeather()
        }

        private fun getCurrentLocationAndWeather() {
            if (hasLocationPermission()) {
                getLastLocation()
            } else {
                requestLocationPermission()
            }
        }

        @SuppressLint("MissingPermission")
        private fun getLastLocation() {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        fetchWeatherData(it.latitude, it.longitude)
                    } ?: run {
                        // Default to Johannesburg if no location
                        fetchWeatherData(-26.2041, 28.0473)
                        Toast.makeText(requireContext(), "Using default location: Johannesburg", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("HomeFragment", "Location error: ${e.message}")
                    fetchWeatherData(-26.2041, 28.0473)
                }
        }

        private fun fetchWeatherData(latitude: Double, longitude: Double) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d("Weather", "Fetching weather for: $latitude, $longitude")

                    val response = RetrofitInstance.openMeteoApi.getCurrentWeather(latitude, longitude)

                    withContext(Dispatchers.Main) {
                        updateWeatherUI(response)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("Weather", "Error fetching weather: ${e.message}")
                        showWeatherError()
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun updateWeatherUI(weatherData: com.example.mining.network.OpenMeteoResponse) {
            binding.apply {
                val temperature = weatherData.current.temperature.toInt()
                val feelsLike = weatherData.current.feelsLike.toInt()
                val weatherCode = weatherData.current.weatherCode
                val weatherDescription = WeatherCodeMapper.getWeatherDescription(weatherCode)
                val weatherIcon = WeatherCodeMapper.getWeatherIcon(weatherCode)
                val humidity = weatherData.current.humidity
                val windSpeed = weatherData.current.windSpeed.toInt()

                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                // Update the date with weather info
                tvDate.text = "Today: $currentDate | $weatherIcon $temperature°C"

                // Update mine site with location and weather details
                val locationName = weatherData.timezone.split("/").last().replace("_", " ")
                tvMineSite.text = "$locationName Mine Site | $weatherDescription"

                // You could also add a weather summary somewhere
                Log.d("Weather", "Weather updated: $weatherDescription, $temperature°C (Feels like $feelsLike°C)")

                // Show weather toast with details
                Toast.makeText(
                    requireContext(),
                    "$weatherIcon $weatherDescription\nTemperature: $temperature°C (Feels like $feelsLike°C)\nHumidity: $humidity% | Wind: $windSpeed km/h",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        private fun showWeatherError() {
            Toast.makeText(requireContext(), "Weather data temporarily unavailable", Toast.LENGTH_SHORT).show()
        }

        private fun hasLocationPermission(): Boolean {
            return ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        private fun requestLocationPermission() {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation()
                } else {
                    fetchWeatherData(-26.2041, 28.0473)
                    Toast.makeText(requireContext(), "Location permission denied. Using Johannesburg.", Toast.LENGTH_LONG).show()
                }
            }
        }

        // ... rest of your existing methods remain exactly the same
        private fun setupQuickActions() {
            val cardIncidentReport = view?.findViewById<CardView>(R.id.card_incident_report)
            val cardSafetyChecklist = view?.findViewById<CardView>(R.id.card_safety_checklist)
            val cardTraining = view?.findViewById<CardView>(R.id.card_training)
            val cardProgressTracking = view?.findViewById<CardView>(R.id.card_progress_tracking)
            val cardEmergency = view?.findViewById<CardView>(R.id.card_emergency)
            val cardEquipmentCheck = view?.findViewById<CardView>(R.id.card_equipment_check)

            cardIncidentReport?.setOnClickListener { navigateToIncidentReport() }
            cardSafetyChecklist?.setOnClickListener { navigateToSafetyChecklist() }
            cardTraining?.setOnClickListener { navigateToTraining() }
            cardProgressTracking?.setOnClickListener { navigateToProgressTracking() }
            cardEmergency?.setOnClickListener { triggerEmergencyProtocol() }
            cardEquipmentCheck?.setOnClickListener { navigateToEquipmentCheck() }
        }

        private fun navigateToIncidentReport() {
            findNavController().navigate(R.id.action_homeFragment_to_incidentReportFragment)
        }

        private fun navigateToSafetyChecklist() {
            findNavController().navigate(R.id.action_homeFragment_to_checklistFragment)
        }

        private fun navigateToTraining() {
            findNavController().navigate(R.id.action_homeFragment_to_trainingFragment)
        }

        private fun navigateToProgressTracking() {
            showProgressDialog()
        }

        private fun triggerEmergencyProtocol() {
            showEmergencyDialog()
        }

        private fun navigateToEquipmentCheck() {
            findNavController().navigate(R.id.action_homeFragment_to_equipmentCheckFragment)
        }

        private fun showProgressDialog() {
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Safety Compliance Progress")
                .setMessage("Overall Safety Compliance: 83%\n\n- Underground Safety: 85%\n- Equipment Maintenance: 72%\n- Training Completion: 93%")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .create()
            dialog.show()
        }

        private fun showEmergencyDialog() {
            val options = arrayOf("Medical Emergency", "Fire", "Cave-in", "Equipment Failure", "Cancel")

            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("EMERGENCY PROTOCOL")
                .setItems(options) { dialog, which ->
                    if (which < 4) {
                        triggerEmergencyAlert(options[which])
                    }
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }

        private fun triggerEmergencyAlert(emergencyType: String) {
            Toast.makeText(requireContext(), "ALERT: $emergencyType reported! Safety team notified.", Toast.LENGTH_LONG).show()

            @Suppress("DEPRECATION") val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            vibrator?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    it.vibrate(1000)
                }
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }