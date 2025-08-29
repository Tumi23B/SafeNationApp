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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.safenation.agriculture.databinding.FragmentReportIncidentBinding
import java.io.File
import java.util.Calendar

class ReportIncidentFragment : Fragment() {

    private var _binding: FragmentReportIncidentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: incidentReportingViewModel by activityViewModels()

    // This block inflates the layout and sets up the view.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportIncidentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // This block sets up listeners and adapters for the form fields.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupIncidentTypeDropdown()
        setupDateTimePickers()
        setupDynamicWitnessFields()
        setupSubmission()
    }

    // This function populates the incident type dropdown.
    private fun setupIncidentTypeDropdown() {
        val incidentTypes = arrayOf("Minor Accident", "Major Accident", "Near Miss", "Property Damage")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, incidentTypes)
        binding.incidentTypeAutocomplete.setAdapter(adapter)
    }

    // This function sets up the date and time picker dialogs.
    private fun setupDateTimePickers() {
        binding.dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                binding.dateInput.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day).show()
        }
        binding.timeInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                binding.timeInput.setText("$selectedHour:$selectedMinute")
            }, hour, minute, true).show()
        }
    }

    // This function handles adding new witness input fields dynamically.
    private fun setupDynamicWitnessFields() {
        binding.addMoreButton.setOnClickListener {
            val witnessLayout = TextInputLayout(requireContext()).apply {
                hint = "Witness Name"
                boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            val witnessInput = TextInputEditText(requireContext())
            witnessLayout.addView(witnessInput)
            binding.witnessContainer.addView(witnessLayout)
        }
    }

    // This function sets up the click listener for the submit button.
    private fun setupSubmission() {
        binding.submitButton.setOnClickListener {
            val incidentType = binding.incidentTypeAutocomplete.text.toString()
            val injuredPerson = binding.injuredPersonInput.text.toString()
            if (incidentType.isNotEmpty() && injuredPerson.isNotEmpty()) {
                viewModel.reportIncident(incidentType)
                Toast.makeText(context, "Incident reported successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please fill all required fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // This block cleans up the binding object when the view is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}