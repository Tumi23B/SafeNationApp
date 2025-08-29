// Purpose: Manages the language settings fragment and its interactions
package com.agricultureAgritech.features.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.FragmentSettLanguageBinding

class FragmentSettLanguage : Fragment() {

    private var _binding: FragmentSettLanguageBinding? = null
    private val binding get() = _binding!!

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Configure views and listeners after the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the toolbar navigation to go back
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        // Set click listener for the save button
        binding.btnSave.setOnClickListener {
            Toast.makeText(context, "Save clicked", Toast.LENGTH_SHORT).show()
            // You can add your save logic here
        }

        // Set initial state and click listeners
        setupLanguageSelection()
    }

    // This function handles the logic for language selection
    private fun setupLanguageSelection() {
        val englishCard = binding.englishCard.root
        val sesothoCard = binding.sesothoCard.root
        val isizuluCard = binding.isizuluCard.root

        val englishRadio: RadioButton = englishCard.findViewById(R.id.rbLanguageSelector)
        val sesothoRadio: RadioButton = sesothoCard.findViewById(R.id.rbLanguageSelector)
        val isizuluRadio: RadioButton = isizuluCard.findViewById(R.id.rbLanguageSelector)

        // Assume English is the default selected language
        englishRadio.isChecked = true

        englishCard.setOnClickListener {
            englishRadio.isChecked = true
            sesothoRadio.isChecked = false
            isizuluRadio.isChecked = false
        }

        sesothoCard.setOnClickListener {
            englishRadio.isChecked = false
            sesothoRadio.isChecked = true
            isizuluRadio.isChecked = false
        }

        isizuluCard.setOnClickListener {
            englishRadio.isChecked = false
            sesothoRadio.isChecked = false
            isizuluRadio.isChecked = true
        }
    }

    // Clean up binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}