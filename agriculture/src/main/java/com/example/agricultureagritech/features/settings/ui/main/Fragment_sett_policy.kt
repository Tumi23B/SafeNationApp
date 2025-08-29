// Purpose: Manages the policy fragment and its interactions
package com.agricultureAgritech.features.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safenation.agriculture.databinding.FragmentSettPolicyBinding

class FragmentSettPolicy : Fragment() {

    private var _binding: FragmentSettPolicyBinding? = null
    private val binding get() = _binding!!

    // This function inflates the layout for the fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    // This function configures views and listeners after the view is created.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This sets up the toolbar's back button to navigate to the previous screen.
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    // This function cleans up the binding when the view is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}