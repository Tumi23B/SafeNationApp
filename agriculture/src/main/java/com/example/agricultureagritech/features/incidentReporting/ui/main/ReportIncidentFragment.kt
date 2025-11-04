// This file contains the logic for the "Report Incident" screen.
package com.example.agricultureagritech.features.incidentReporting.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.agricultureagritech.features.incidentReporting.data.model.AgriIncident
import com.example.core.SupabaseClient
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.FragmentReportIncidentBinding
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.agricultureagritech.features.incidentReporting.ui.IncidentReportingViewModel

class ReportIncidentFragment : Fragment() {

    private var _binding: FragmentReportIncidentBinding? = null
    private val binding get() = _binding!!

    // This ViewModel is available if needed for other state management.
    private val viewModel: IncidentReportingViewModel by activityViewModels()

    // This block inflates the layout and sets up the view.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportIncidentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // This block sets up listeners and adapters for the form fields.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIncidentTypeDropdown()
        setupSeverityDropdown()
        setupDateTimePickers()

        binding.submitButton.setOnClickListener {
            submitIncidentReport()
        }
    }

    // This function populates the incident type dropdown.
    private fun setupIncidentTypeDropdown() {
        val incidentTypes = resources.getStringArray(R.array.incident_types)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, incidentTypes)
        binding.incidentTypeAutocomplete.setAdapter(adapter)
    }

    // This function populates the severity dropdown.
    // This function populates the severity dropdown.
    private fun setupSeverityDropdown() {
        val severityLevels = resources.getStringArray(R.array.severity_levels)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, severityLevels)
        binding.severityAutocomplete.setAdapter(adapter)
    }

    // This function sets up the date and time picker dialogs.
    private fun setupDateTimePickers() {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                // Format date as YYYY-MM-DD for database compatibility
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.dateInput.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        binding.dateInput.setOnClickListener {
            datePicker.show()
        }

        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hour)
                selectedTime.set(Calendar.MINUTE, minute)
                // Format time as HH:mm:ss for database compatibility
                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                binding.timeInput.setText(timeFormat.format(selectedTime.time))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // 24-hour format
        )

        binding.timeInput.setOnClickListener {
            timePicker.show()
        }
    }

    /**
     * Collects data from form, validates it, and submits to Supabase.
     */
    private fun submitIncidentReport() {
        // 1. Get data from form fields using correct view IDs
        val incidentType = binding.incidentTypeAutocomplete.text.toString()
        val date = binding.dateInput.text.toString()
        val time = binding.timeInput.text.toString()
        val location = binding.locationInput.text.toString()
        val description = binding.descriptionInput.text.toString()
        val severity = binding.severityAutocomplete.text.toString()
        val witnessName = binding.witnessNameInput.text.toString()
        val witnessContact = binding.witnessContactInput.text.toString()

        // 2. Basic Validation
        if (incidentType.isBlank() || date.isBlank() || time.isBlank() || location.isBlank() || severity.isBlank()) {
            Toast.makeText(requireContext(), getString(R.string.toast_fill_required_fields), Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Create data model
        val newIncident = AgriIncident(
            incidentType = incidentType,
            date = date,
            time = time,
            location = location,
            description = description.takeIf { it.isNotBlank() },
            severity = severity,
            witnessName = witnessName.takeIf { it.isNotBlank() },
            witnessContact = witnessContact.takeIf { it.isNotBlank() },
            status = "Issued" // Set default status for new reports
        )

        // 4. Launch coroutine for database operation
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Access the shared Supabase client
                val supabase = SupabaseClient.client

                // Insert the new incident.
                // We pass 'AgriIncident' directly.
                // Supabase matches the @SerialName tags to the table columns.
                supabase.postgrest.from("agri_incidents").insert(newIncident)

                // 5. Handle Success
                Toast.makeText(requireContext(), getString(R.string.toast_report_success), Toast.LENGTH_LONG).show()
                clearForm()

                // 6. Notify the ViewModel to refresh the list on the other tab
                viewModel.fetchAllIncidents()

            } catch (e: Exception) {
                // 7. Handle Error
                val errorMessage = getString(R.string.toast_report_error, e.message)
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    /**
     * Clears all input fields after a successful submission.
     */
    private fun clearForm() {
        binding.incidentTypeAutocomplete.setText("", false)
        binding.dateInput.text?.clear()
        binding.timeInput.text?.clear()
        binding.locationInput.text?.clear()
        binding.descriptionInput.text?.clear()
        binding.severityAutocomplete.setText("", false)
        binding.witnessNameInput.text?.clear()
        binding.witnessContactInput.text?.clear()
    }

    // This block cleans up the binding object when the view is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}