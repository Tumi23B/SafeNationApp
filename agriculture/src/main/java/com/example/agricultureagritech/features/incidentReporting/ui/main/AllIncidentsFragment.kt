// This file displays the list of all reported incidents.
package com.example.agricultureagritech.features.incidentReporting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.safenation.agriculture.databinding.FragmentAllIncidentsBinding


class AllIncidentsFragment : Fragment() {

    private var _binding: FragmentAllIncidentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: incidentReportingViewModel by activityViewModels()
    private lateinit var incidentAdapter: IncidentAdapter

    // This block inflates the layout using data binding.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllIncidentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // This block sets up the RecyclerView and observes data changes.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    // This function configures the RecyclerView and its adapter.
    private fun setupRecyclerView() {
        incidentAdapter = IncidentAdapter(emptyList())
        binding.incidentsRecyclerView.apply {
            adapter = incidentAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    // This function observes the ViewModel for incident list updates.
    private fun observeViewModel() {
        viewModel.incidents.observe(viewLifecycleOwner) { incidents ->
            incidentAdapter.updateIncidents(incidents)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // You can add a ProgressBar to your layout and control its visibility here.
        }
    }

    // This block cleans up the binding object to avoid memory leaks.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}