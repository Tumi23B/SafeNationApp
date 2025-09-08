package com.safenation.mining.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.safenation.mining.R
import com.safenation.mining.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set current date
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.tvDate.text = "Today: $currentDate"

        // Set click listeners for quick actions
        setupQuickActions()

        // Set click listener for view all tasks button
        binding.btnViewAllTasks.setOnClickListener {
            // Navigate to tasks fragment
            findNavController().navigate(R.id.action_homeFragment_to_tasksFragment)
        }
    }

    private fun setupQuickActions() {
        // Get references to all quick action cards
        val cardIncidentReport = view?.findViewById<CardView>(R.id.card_incident_report)
        val cardSafetyChecklist = view?.findViewById<CardView>(R.id.card_safety_checklist)
        val cardTraining = view?.findViewById<CardView>(R.id.card_training)
        val cardProgressTracking = view?.findViewById<CardView>(R.id.card_progress_tracking)
        val cardEmergency = view?.findViewById<CardView>(R.id.card_emergency)
        val cardEquipmentCheck = view?.findViewById<CardView>(R.id.card_equipment_check)

        // Set click listeners
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
        // Show progress tracking dialog or navigate to progress fragment
        showProgressDialog()
    }

    private fun triggerEmergencyProtocol() {
        // Show emergency dialog with options
        showEmergencyDialog()
    }

    private fun navigateToEquipmentCheck() {
        // Navigate to equipment check fragment
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
                if (which < 4) { // Not the Cancel option
                    triggerEmergencyAlert(options[which])
                }
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun triggerEmergencyAlert(emergencyType: String) {
        // In a real app, this would trigger notifications to safety personnel
        Toast.makeText(requireContext(), "ALERT: $emergencyType reported! Safety team notified.", Toast.LENGTH_LONG).show()

        // Check if we have vibration permission and device supports vibration
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