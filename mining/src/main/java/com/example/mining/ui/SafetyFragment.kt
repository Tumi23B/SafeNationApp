package com.safenation.mining.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.safenation.mining.R
import com.safenation.mining.databinding.FragmentSafetyBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SafetyFragment : Fragment() {
    private var _binding: FragmentSafetyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSafetyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Emergency contact call buttons - use binding instead of findViewById
        binding.btnCallSafetyOfficer.setOnClickListener {
            makeEmergencyCall("5550123")
        }

        binding.btnCallMedical.setOnClickListener {
            makeEmergencyCall("5559111")
        }

        binding.btnCallRescue.setOnClickListener {
            makeEmergencyCall("555737283") // RESCUE
        }

        // Emergency procedure buttons
        binding.btnViewEvacuationPlan.setOnClickListener {
            viewEvacuationPlan()
        }

        binding.btnEmergencyProtocols.setOnClickListener {
            viewEmergencyProtocols()
        }

        binding.btnFirstAidGuide.setOnClickListener {
            viewFirstAidGuide()
        }

        binding.btnReportSafetyIssue.setOnClickListener {
            reportSafetyIssue()
        }
    }

    private fun makeEmergencyCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
           // Intent.setData = Uri.parse("tel:$phoneNumber")
            data = Uri.parse("tel:$phoneNumber")
        }

        // Show confirmation dialog
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Emergency Call")
            .setMessage("Call emergency number: $phoneNumber?")
            .setPositiveButton("Call") { dialog, _ ->
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun viewEvacuationPlan() {
        // Navigate to evacuation plan PDF/viewer
        Toast.makeText(requireContext(), "Opening evacuation plan...", Toast.LENGTH_SHORT).show()

        // In a real app, this would open a PDF or detailed evacuation map
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Evacuation Plan")
            .setMessage("Primary evacuation routes:\n\n" +
                    "1. Main shaft elevator to surface\n" +
                    "2. Emergency ladder ways\n" +
                    "3. Secondary escape routes (marked)\n\n" +
                    "Assembly point: North parking lot")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
        dialog.show()
    }

    private fun viewEmergencyProtocols() {
        // Show emergency protocols
        val protocols = listOf(
            "1. Sound alarm immediately",
            "2. View evacuation routes",
            "3. Account for all personnel",
            "4. Do not use elevators during emergency",
            "5. Follow instructions from safety officers"
        )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Emergency Protocols")
            .setItems(protocols.toTypedArray()) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun viewFirstAidGuide() {
        // Show first aid information
        Toast.makeText(requireContext(), "Opening first aid guide...", Toast.LENGTH_SHORT).show()

        val firstAidInfo = """
            Basic First Aid Procedures:
            
            • Stop bleeding with direct pressure
            • Do not move injured unless in danger
            • CPR if trained and necessary
            • Treat for shock - keep warm
            • Wait for medical professionals
            
            Emergency medical kit locations:
            - Every 100m in main tunnels
            - All equipment cabs
            - Safety officer stations
        """.trimIndent()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("First Aid Guide")
            .setMessage(firstAidInfo)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun reportSafetyIssue() {
        // Navigate to safety issue reporting
        Toast.makeText(requireContext(), "Opening safety issue report...", Toast.LENGTH_SHORT).show()

        // This could navigate to a dedicated reporting fragment
        findNavController().navigate(R.id.action_safetyFragment_to_incidentReportFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}