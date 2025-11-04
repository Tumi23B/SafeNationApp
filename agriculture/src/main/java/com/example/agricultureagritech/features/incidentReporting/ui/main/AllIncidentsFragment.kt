// This fragment displays a list of all incidents with a filter spinner
package com.agricultureAgritech.features.incidentReporting.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agricultureagritech.features.incidentReporting.ui.IncidentAdapter
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.FragmentAllIncidentsBinding
import com.example.agricultureagritech.features.incidentReporting.data.model.Incident
import com.example.agricultureagritech.features.incidentReporting.ui.IncidentReportingViewModel

class AllIncidentsFragment : Fragment() {

    private var _binding: FragmentAllIncidentsBinding? = null
    private val binding get() = _binding!!

    // Use activityViewModels to share the ViewModel with the parent Activity and other Fragment
    private val viewModel: IncidentReportingViewModel by activityViewModels()
    private lateinit var incidentAdapter: IncidentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllIncidentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the filter chips
        setupStatusFilter()

        // Setup the RecyclerView
        setupRecyclerView()

        // Observe the LiveData from the ViewModel
        viewModel.incidents.observe(viewLifecycleOwner) { incidents ->
            // Submit an empty list if incidents is null
            incidentAdapter.submitList(incidents ?: emptyList<Incident>())
        }

        // Fetch incidents when the fragment is created
        viewModel.fetchAllIncidents()
    }

    /*
     * Sets up the status filter spinner using the string array resource.
     * This ensures the spinner options are translated.
     */
    /*
 * Sets up listeners for the status filter ChipGroup.
 */
    private fun setupStatusFilter() {
        // Set a listener on the chip group to respond to new selections
        binding.filterChipGroup.setOnCheckedChangeListener { group, checkedId ->
            // Determine the status string based on which chip was selected
            val status = when (checkedId) {
                R.id.chip_issued -> "Issued"
                R.id.chip_pending -> "Pending"
                R.id.chip_resolved -> "Resolved"
                else -> "All" // Default to "All" for R.id.chip_all or no selection
            }
            // Call the ViewModel to apply the filter
            viewModel.filterIncidents(status)
        }
    }

    /*
     * Configures the RecyclerView, adapter, and layout manager.
     */
    private fun setupRecyclerView() {
        incidentAdapter = IncidentAdapter { incident ->
            // Handle incident click if needed, e.g., navigate to detail screen
        }
        binding.incidentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = incidentAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}