// This file defines the main activity for the incident reporting feature.
package com.safenation.agriculture.features.incidentReporting.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.example.agricultureagritech.features.incidentReporting.ui.IncidentPagerAdapter
import com.example.agricultureagritech.features.incidentReporting.ui.incidentReportingViewModel
import com.example.safenationapp.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.safenation.agriculture.databinding.ActivityIncidentReportingBinding

class IncidentReportingActivity : BaseActivity() {

    private lateinit var binding: ActivityIncidentReportingBinding
    private val viewModel: incidentReportingViewModel by viewModels()

    /*
     * This function sets up the view, toolbar, tabs, and fragments for incident reporting.
     * It also applies window insets to avoid overlapping with system bars.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidentReportingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyWindowInsets(binding.root)

        binding.toolbarIncidents.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val adapter = IncidentPagerAdapter(this)
        binding.viewPagerIncidents.adapter = adapter

        TabLayoutMediator(binding.tabsIncidents, binding.viewPagerIncidents) { tab, position ->
            tab.text = when (position) {
                0 -> "All Incidents"
                1 -> "Report Incident"
                else -> null
            }
        }.attach()
    }
}