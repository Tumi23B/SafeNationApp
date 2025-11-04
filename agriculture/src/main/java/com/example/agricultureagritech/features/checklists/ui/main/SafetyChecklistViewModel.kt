// This file provides the ViewModel for the safety checklist feature.
package com.agricultureAgritech.features.safetyChecklists.ui

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.agricultureAgritech.features.safetyChecklists.domain.model.ChecklistItem
import com.safenation.agriculture.R

class SafetyChecklistViewModel(application: Application) : AndroidViewModel(application) {

    private val _checklistItems = MutableLiveData<List<ChecklistItem>>()
    val checklistItems: LiveData<List<ChecklistItem>> = _checklistItems

    /*
     * This init block loads the checklist items when the ViewModel is created.
     */
    init {
        loadChecklistItems()
    }

    /*
     * This function populates the checklist with data from string resources.
     */
    private fun loadChecklistItems() {
        val res = getApplication<Application>().resources
        val color = Color.parseColor("#606C38")

        val items = listOf(
            ChecklistItem(
                res.getString(R.string.checklist_tractor_title),
                res.getString(R.string.checklist_tractor_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_pesticide_title),
                res.getString(R.string.checklist_pesticide_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_harvesting_title),
                res.getString(R.string.checklist_harvesting_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_livestock_title),
                res.getString(R.string.checklist_livestock_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_emergency_title),
                res.getString(R.string.checklist_emergency_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_fit_for_work_title),
                res.getString(R.string.checklist_fit_for_work_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_training_title),
                res.getString(R.string.checklist_training_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_weather_title),
                res.getString(R.string.checklist_weather_subtitle),
                color
            ),
            ChecklistItem(
                res.getString(R.string.checklist_hazard_title),
                res.getString(R.string.checklist_hazard_subtitle),
                color
            ),
        )
        _checklistItems.value = items
    }
}