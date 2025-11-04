// This file contains the logic for the dashboard screen.
package com.safenation.agriculture.features.dashboard.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.example.safenationapp.BaseActivity
import com.safenation.agriculture.R
import com.safenation.agriculture.features.incidentReporting.ui.IncidentReportingActivity
import com.safenation.agriculture.features.safetyChecklists.ui.SafetyChecklistActivity
import com.safenation.agriculture.features.settings.ui.SettingsActivity
import com.safenation.agriculture.features.training.ui.TrainingModuleActivity


class DashboardActivity : BaseActivity() {
    /*
     * This function sets up the dashboard, handles window insets, and configures navigation.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        applyWindowInsets(findViewById(R.id.dashboard_screen))

        findViewById<ImageView?>(R.id.settings_icon)?.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<ImageView?>(R.id.checklist_img)?.setOnClickListener {
            startActivity(Intent(this, SafetyChecklistActivity::class.java))
        }

        findViewById<ImageView?>(R.id.training_img)?.setOnClickListener {
            startActivity(Intent(this, TrainingModuleActivity::class.java))
        }

        findViewById<ImageView?>(R.id.repo_incident)?.setOnClickListener {
            startActivity(Intent(this, IncidentReportingActivity::class.java))
        }
    }
}