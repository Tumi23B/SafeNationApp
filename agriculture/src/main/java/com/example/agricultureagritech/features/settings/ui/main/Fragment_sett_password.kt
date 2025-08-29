// Purpose: Manages the change password fragment and its interactions
package com.agricultureAgritech.features.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.safenation.agriculture.databinding.FragmentSettPasswordBinding

class FragmentSettPassword : Fragment() {

    private var _binding: FragmentSettPasswordBinding? = null
    private val binding get() = _binding!!

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Configure views and listeners after the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the toolbar navigation to go back
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        // Set click listener for the update password button
        binding.btnUpdatePassword.setOnClickListener {
            Toast.makeText(context, "Update Password clicked", Toast.LENGTH_SHORT).show()
        }
    }

    // Clean up binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}