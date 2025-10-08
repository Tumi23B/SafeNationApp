// Purpose: Bind Settings layout and wire interactions
package com.agricultureAgritech.features.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // This function inflates the layout for the fragment.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // This function configures views and listeners after the view is created.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This binds data to the layout.
        binding.language = getString(R.string.settings_language)
        binding.changePassword = getString(R.string.settings_change_password)
        binding.policy = getString(R.string.settings_policy)

        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        // This sets click listeners for the interactive elements.
        binding.accountCard.root.setOnClickListener {
            Toast.makeText(context, "Account clicked", Toast.LENGTH_SHORT).show()
        }
        binding.languageRow.root.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentSettLanguage())
                .addToBackStack(null)
                .commit()
        }
        binding.changePasswRow.root.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentSettPassword())
                .addToBackStack(null)
                .commit()
        }
        binding.policyRow.root.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentSettPolicy())
                .addToBackStack(null)
                .commit()
        }
        binding.btnLogout.setOnClickListener {
            Toast.makeText(context, "Logout clicked", Toast.LENGTH_SHORT).show()

        }
    }

    // This function cleans up the binding when the view is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}