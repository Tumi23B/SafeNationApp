// This file sets up the incident reporting screen, including the ViewPager and tabs.
package com.agricultureAgritech.features.incidentReporting.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.agricultureagritech.features.incidentReporting.ui.IncidentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.ActivityIncidentReportingBinding

class IncidentReportingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIncidentReportingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_incident_reporting)

        // Set up the toolbar
        setSupportActionBar(binding.toolbarIncidents)

        // Set up the toolbar
        setSupportActionBar(binding.toolbarIncidents)

        // Set the title using the string resource
        supportActionBar?.title = getString(R.string.incidents_title)

        // Handle the toolbar's navigation (back button)
        binding.toolbarIncidents.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Set up the ViewPager and TabLayout
        setupViewPagerAndTabs()
    }

    /*
     * Configures the ViewPager2 adapter and links it to the TabLayout.
     * This is where the static tab titles are set using string resources.
     */
    private fun setupViewPagerAndTabs() {
        // Initialize the adapter for the ViewPager
        val pagerAdapter = IncidentPagerAdapter(this)
        binding.viewPagerIncidents.adapter = pagerAdapter

        // Use TabLayoutMediator to link the tabs to the ViewPager
        TabLayoutMediator(binding.tabsIncidents, binding.viewPagerIncidents) { tab, position ->
            // Set the tab title based on its position
            tab.text = when (position) {
                0 -> getString(R.string.incident_tab_all) // Use string resource
                1 -> getString(R.string.incident_tab_report) // Use string resource
                else -> null
            }
        }.attach() // Attach the mediator to link them
    }
}