// This file manages the data for the incident reporting UI.
package com.example.agricultureagritech.features.incidentReporting.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agricultureagritech.features.incidentReporting.data.model.AgriIncident
import com.example.agricultureagritech.features.incidentReporting.data.model.Incident
import com.example.core.SupabaseClient
import com.safenation.agriculture.R
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

// Add 'Application' to the constructor and make it an 'AndroidViewModel'
// Renamed class to use PascalCase
class IncidentReportingViewModel(application: Application) : AndroidViewModel(application) {

    // Holds the complete list fetched from Supabase
    private val _allIncidents = MutableLiveData<List<AgriIncident>>()

    // Holds the list displayed in the UI, which can be filtered
    private val _incidents = MutableLiveData<List<Incident>>()
    val incidents: LiveData<List<Incident>> = _incidents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchIncidents()
    }

    /**
     * Fetches all incidents from the Supabase "agri_incidents" table.
     */
    private fun fetchIncidents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch data from Supabase
                val supabaseResponse = SupabaseClient.client.postgrest
                    .from("agri_incidents")
                    .select {
                        order("created_at", Order.DESCENDING) // Show newest first
                    }
                    .decodeList<AgriIncident>() // Deserialize into our data model

                _allIncidents.value = supabaseResponse
                // Map the database model (AgriIncident) to the UI model (Incident)
                _incidents.value = mapToUiModel(supabaseResponse)

            } catch (e: Exception) {
                Log.e("IncidentViewModel", "Error fetching incidents", e)
                _allIncidents.value = emptyList()
                _incidents.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Public function to allow UI (e.g., ReportIncidentFragment) to trigger a refresh.
     */
    /**
     * Public function to allow UI (e.g., AllIncidentsFragment) to trigger a refresh.
     */
    fun fetchAllIncidents() { // Renamed to match the call in AllIncidentsFragment
        fetchIncidents()
    }

    /**
     * Filters the displayed incident list based on the selected status.
     * @param status The status to filter by ("All", "Issued", "Pending", "Resolved").
     */
    fun filterIncidents(status: String) {
        if (status == "All") {
            _incidents.value = mapToUiModel(_allIncidents.value ?: emptyList())
            return
        }

        val filteredList = _allIncidents.value?.filter {
            it.status.equals(status, ignoreCase = true)
        }
        _incidents.value = mapToUiModel(filteredList ?: emptyList())
    }

    /**
     * Maps the database model (AgriIncident) to the UI model (Incident).
     * This decouples the database schema from the UI display.
     */
    /**
     * Maps the database model (AgriIncident) to the UI model (Incident).
     * This decouples the database schema from the UI display.
     */
    private fun mapToUiModel(dbIncidents: List<AgriIncident>): List<Incident> {
        val app = getApplication<Application>()
        return dbIncidents.map { agriIncident ->
            Incident(
                // Use severity for priority (e.g., "High")
                priority = agriIncident.severity,
                // Use the database ID
                id = agriIncident.id?.toString() ?: app.getString(R.string.status_na),
                // Use description as the main title, or incident type as fallback
                title = agriIncident.description ?: agriIncident.incidentType,
                // Combine date and time for the timestamp
                timestamp = "${agriIncident.date}, ${agriIncident.time}",
                // Use witness name for owner, or "Unassigned" as fallback
                owner = agriIncident.witnessName ?: app.getString(R.string.owner_unassigned),
                // Pass the status directly
                status = agriIncident.status
            )
        }
    }
}