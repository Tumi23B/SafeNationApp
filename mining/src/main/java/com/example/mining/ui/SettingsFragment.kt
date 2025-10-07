package com.safenation.mining.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.safenation.mining.MainActivity
import com.safenation.mining.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

        private var _binding: FragmentSettingsBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            _binding = FragmentSettingsBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
                val language = if (isChecked) "IsiZulu" else "English"
                Toast.makeText(requireContext(), "Language set to: $language", Toast.LENGTH_SHORT).show()
            }

            binding.btnGenerateWeeklyReport.setOnClickListener {
                generateReport("weekly")
            }

            binding.btnGenerateMonthlyReport.setOnClickListener {
                generateReport("monthly")
            }

            binding.btnLogout.setOnClickListener {
                // Show confirmation dialog before logout
                showLogoutConfirmation()
            }
        }

        private fun generateReport(type: String) {
            Toast.makeText(requireContext(), "Generating $type report...", Toast.LENGTH_SHORT).show()
        }

        private fun showLogoutConfirmation() {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout") { dialog, _ ->
                    performLogout()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        private fun performLogout() {
            // Call the MainActivity's handleLogout method
            Log.d("NavigationDebug", "performLogout called in SettingsFragment")
            // Call the MainActivity's handleLogout method
            (requireActivity() as? MainActivity)?.handleLogout()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }