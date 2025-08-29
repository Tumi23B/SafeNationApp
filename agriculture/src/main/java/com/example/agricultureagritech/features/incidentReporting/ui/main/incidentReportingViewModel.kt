// This file manages the data for the incident reporting UI.
package com.example.agricultureagritech.features.incidentReporting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agricultureagritech.features.incidentReporting.data.model.Incident
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class incidentReportingViewModel : ViewModel() {

    // This block declares the LiveData for incidents and loading state.
    private val _incidents = MutableLiveData<List<Incident>>()
    val incidents: LiveData<List<Incident>> = _incidents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchIncidents()
    }

    // This function fetches a list of incidents.
    fun fetchIncidents() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000) // Simulate network delay.
            _incidents.value = listOf(
                Incident("INC001", "Leaking pipe in Section A", "High", "2025-08-25 20:30:00", "Lethabo Rema", "Issued"),
                Incident("INC002", "Safety gear missing", "Medium", "2025-08-25 18:00:00", "Jane Smith", "Pending"),
                Incident("INC003", "Fence broken near West gate", "Low", "2025-08-24 12:00:00", "Melusi Feka", "Resolved")
            )
            _isLoading.value = false
        }
    }

    // This function simulates reporting a new incident.
    fun reportIncident(description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1500)
            fetchIncidents()
        }
    }
}