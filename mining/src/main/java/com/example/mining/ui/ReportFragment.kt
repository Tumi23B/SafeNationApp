package com.safenation.mining.ui





import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.safenation.mining.databinding.FragmentReportBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Date
import java.util.Locale

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private var selectedLocation: GeoPoint? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)

        // Initialize osmdroid configuration
        Configuration.getInstance().load(requireContext(), androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set current date as default
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.etIncidentDate.setText(currentDate)

        // Initialize map
        setupMap()

        binding.btnSubmitIncident.setOnClickListener {
            submitIncidentReport()
        }

        binding.btnTakePhoto.setOnClickListener {
            // Implement camera functionality
            Toast.makeText(requireContext(), "Camera functionality would open here", Toast.LENGTH_SHORT).show()
        }

        binding.btnChoosePhoto.setOnClickListener {
            // Implement gallery picker
            Toast.makeText(requireContext(), "Gallery would open here", Toast.LENGTH_SHORT).show()
        }

        binding.btnGetCurrentLocation.setOnClickListener {
            getCurrentLocation()
        }
    }

    private fun setupMap() {
        mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK) // OpenStreetMap tiles
        mapView.setMultiTouchControls(true)

        // Set initial view (you can set to a default location or use device location)
        val startPoint = GeoPoint(-1.2921, 36.8219) // Default to Nairobi, Kenya
        mapView.controller.setZoom(15.0)
        mapView.controller.setCenter(startPoint)

        // Add map click listener to select location
        mapView.overlays.add(org.osmdroid.views.overlay.gestures.RotationGestureOverlay(mapView))

        // Corrected map click listener
        mapView.overlays.add(object : org.osmdroid.views.overlay.Overlay() {
            override fun onSingleTapConfirmed(e: MotionEvent, mapView: MapView): Boolean {
                e?.let {
                    val iGeoPoint = mapView?.projection?.fromPixels(e.x.toInt(), e.y.toInt())
                    iGeoPoint?.let {
                        // Convert IGeoPoint to GeoPoint
                        val geoPoint = GeoPoint(iGeoPoint.latitude, iGeoPoint.longitude)
                        setSelectedLocation(geoPoint)
                    }
                }
                return true
            }
        })
    }

    private fun setSelectedLocation(geoPoint: GeoPoint) {
        selectedLocation = geoPoint

        // Clear existing markers
        mapView.overlays.removeIf { it is Marker }

        // Add new marker
        val marker = Marker(mapView)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Incident Location"
        mapView.overlays.add(marker)
        mapView.invalidate()

        Toast.makeText(requireContext(),
            "Location selected: ${geoPoint.latitude}, ${geoPoint.longitude}",
            Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentLocation() {
        // This is a simplified version - in a real app, you'd use FusedLocationProviderClient
        Toast.makeText(requireContext(), "Getting current location...", Toast.LENGTH_SHORT).show()

        // For demo purposes, we'll just center on a default location
        // In a real app, you'd implement proper location services
        val currentLocation = GeoPoint(-1.2921, 36.8219) // Example coordinates
        mapView.controller.animateTo(currentLocation)
        setSelectedLocation(currentLocation)
    }

    private fun submitIncidentReport() {
        val title = binding.etIncidentTitle.text.toString()
        binding.etIncidentDate.text.toString()
        val description = binding.etIncidentDescription.text.toString()

        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Include location data if selected
        selectedLocation?.let { location ->
            val lat = location.latitude
            val lon = location.longitude
            // Save location data with your incident report
            Toast.makeText(requireContext(),
                "Report submitted with location: $lat, $lon",
                Toast.LENGTH_SHORT).show()
        }

        // Here you would save the incident report to your database
        Toast.makeText(requireContext(), "Incident report submitted successfully!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}