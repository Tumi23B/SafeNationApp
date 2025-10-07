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

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            _binding = FragmentReportBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Set current date as default
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            binding.etIncidentDate.setText(currentDate)

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
        }

        private fun submitIncidentReport() {
            val title = binding.etIncidentTitle.text.toString()
            val date = binding.etIncidentDate.text.toString()
            val description = binding.etIncidentDescription.text.toString()

            if (title.isBlank() || description.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return
            }

            // Here you would save the incident report to your database
            Toast.makeText(requireContext(), "Incident report submitted successfully!", Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().popBackStack()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }