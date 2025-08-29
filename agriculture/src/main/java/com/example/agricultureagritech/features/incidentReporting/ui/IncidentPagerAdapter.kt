// This file defines the adapter that manage the fragments
package com.example.agricultureagritech.features.incidentReporting.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// This adapter provides the correct fragment for each tab.
class IncidentPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // This returns the total number of tabs.
    override fun getItemCount(): Int = 2

    // This creates the fragment for a given position.
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllIncidentsFragment()
            1 -> ReportIncidentFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}