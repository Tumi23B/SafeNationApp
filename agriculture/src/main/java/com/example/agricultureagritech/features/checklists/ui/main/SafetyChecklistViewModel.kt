// This file provides the ViewModel for the safety checklist feature.
package com.agricultureAgritech.features.safetyChecklists.ui

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agricultureAgritech.features.safetyChecklists.domain.model.ChecklistItem

class SafetyChecklistViewModel : ViewModel() {

    private val _checklistItems = MutableLiveData<List<ChecklistItem>>()
    val checklistItems: LiveData<List<ChecklistItem>> = _checklistItems

    /*
     * This init block loads the checklist items when the ViewModel is created.
     */
    init {
        loadChecklistItems()
    }

    /*
     * This function populates the checklist with sample data.
     */
    private fun loadChecklistItems() {
        val items = listOf(
            ChecklistItem("Tractor Operation", "Pre-operation checks | Safe driving", Color.parseColor("#606C38")),
            ChecklistItem("Pesticide Handling", "Proper PPE | Storage guidelines", Color.parseColor("#606C38")),
            ChecklistItem("Harvesting Safety", "Equipment inspection | Fatigue management", Color.parseColor("#606C38")),
            ChecklistItem("Livestock Handling", "Animal behavior | Proper restraints", Color.parseColor("#606C38")),
            ChecklistItem("Emergency Preparedness", "First aid kits | Evacuation routes", Color.parseColor("#606C38")),
            ChecklistItem("Fit For Work", "Policy | Worker Capable of performing tasks", Color.parseColor("#606C38")),
            ChecklistItem("Training", "Training Module | Operate equipment and machinery safely", Color.parseColor("#606C38")),
            ChecklistItem("Weather Awareness", "Precautions | Changing weather conditions", Color.parseColor("#606C38")),
            ChecklistItem("Hazard Warning", "Post Labels and Signs | Warning signs", Color.parseColor("#606C38")),
        )
        _checklistItems.value = items
    }
}