// Purpose: Manages the language settings fragment and its interactions
package com.agricultureAgritech.features.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.FragmentSettLanguageBinding

class FragmentSettLanguage : Fragment() {

    private var _binding: FragmentSettLanguageBinding? = null
    private val binding get() = _binding!!

    // This variable will hold the language tag to be saved, as, "en", "zu", "st"
    private var selectedLanguageTag: String = "en"

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
            // Set the app's language to the one selected
            setAppLocale(selectedLanguageTag)
            Toast.makeText(context, "Language updated", Toast.LENGTH_SHORT).show()
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

        // Get the app's current language to set the correct radio button
        val currentLang = AppCompatDelegate.getApplicationLocales().toLanguageTags().split(",").firstOrNull() ?: "en"
        selectedLanguageTag = currentLang // Initialize the selection

        // Set the correct radio button based on the current language
        when {
            currentLang.contains("zu") -> {
                isizuluRadio.isChecked = true
                selectedLanguageTag = "zu"
            }
            currentLang.contains("st") -> {
                sesothoRadio.isChecked = true
                selectedLanguageTag = "st"
            }
            else -> {
                englishRadio.isChecked = true
                selectedLanguageTag = "en"
            }
        }

        // Add listeners to update the radio buttons and the selectedLanguageTag variable
        englishCard.setOnClickListener {
            englishRadio.isChecked = true
            sesothoRadio.isChecked = false
            isizuluRadio.isChecked = false
            selectedLanguageTag = "en"
        }

        sesothoCard.setOnClickListener {
            englishRadio.isChecked = false
            sesothoRadio.isChecked = true
            isizuluRadio.isChecked = false
            selectedLanguageTag = "st"
        }

        isizuluCard.setOnClickListener {
            englishRadio.isChecked = false
            sesothoRadio.isChecked = false
            isizuluRadio.isChecked = true
            selectedLanguageTag = "zu"
        }
    }

    // This function tells the system to change the app's language
    private fun setAppLocale(languageTag: String) {
        val locales = LocaleListCompat.forLanguageTags(languageTag)
        AppCompatDelegate.setApplicationLocales(locales)
        // The system will automatically recreate the Activity to apply the new language
    }

    // Clean up binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}