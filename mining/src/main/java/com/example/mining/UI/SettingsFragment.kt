package com.example.mining.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mining.R
import com.example.mining.databinding.FragmentSettingsBinding


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
            val language = if (isChecked) "Spanish" else "English"
            Toast.makeText(requireContext(), "Language set to: $language", Toast.LENGTH_SHORT).show()
        }

        binding.btnGenerateWeeklyReport.setOnClickListener {
            generateReport("weekly")
        }

        binding.btnGenerateMonthlyReport.setOnClickListener {
            generateReport("monthly")
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun generateReport(type: String) {
        Toast.makeText(requireContext(), "Generating $type report...", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        // Clear user session and navigate to login
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
        // findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}