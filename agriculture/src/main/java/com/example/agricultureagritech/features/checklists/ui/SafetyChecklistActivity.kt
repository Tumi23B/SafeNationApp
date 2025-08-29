// This file contains the logic for the safety checklist screen.
package com.safenation.agriculture.features.safetyChecklists.ui

import android.os.Bundle
import com.agricultureAgritech.features.safetyChecklists.ui.SafetyChecklistFragment
import com.example.safenationapp.BaseActivity
import com.safenation.agriculture.R

class SafetyChecklistActivity : BaseActivity() {
    /*
     * This function sets the layout and applies window insets.
     * It also loads the SafetyChecklistFragment.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safety_checklist)
        applyWindowInsets(findViewById(R.id.fragment_container))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SafetyChecklistFragment())
                .commit()
        }
    }
}