// This file contains the UI logic for the Safety Checklist screen.
package com.agricultureAgritech.features.safetyChecklists.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.safenation.agriculture.databinding.FragmentSafetyChecklistBinding
import java.io.File

class SafetyChecklistFragment : Fragment() {

    private var _binding: FragmentSafetyChecklistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SafetyChecklistViewModel by viewModels()

    /*
     * This function inflates the layout for the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSafetyChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
     * This function sets up the views and observes data changes after the view is created.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "List stored!", Toast.LENGTH_SHORT).show()
        }
    }

    /*
     * This function initializes the RecyclerView with a layout manager.
     */
    private fun setupRecyclerView() {
        binding.safetyChecklistRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    /*
     * This function observes the checklist items from the ViewModel and updates the adapter.
     */
    private fun observeViewModel() {
        viewModel.checklistItems.observe(viewLifecycleOwner) { items ->
            binding.safetyChecklistRecyclerView.adapter = SafetyChecklistAdapter(items)
        }
    }

    /*
     * This function cleans up the binding when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}