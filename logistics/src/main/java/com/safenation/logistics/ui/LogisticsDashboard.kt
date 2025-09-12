package com.safenation.logistics.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.safenation.logistics.R
import com.google.android.material.card.MaterialCardView

class LogisticsDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for immersive UI
        enableEdgeToEdge()
        setContentView(R.layout.activity_logistics_dashboard)

        // Handle system insets (status bar, nav bar, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard_screen)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize and bind views
        initializeViews()
    }

    private fun initializeViews() {
        // Sections from XML (declared as LinearLayouts, not CardViews)
        val vehicleInspection = findViewById<LinearLayout?>(R.id.vehicleInspectionSection)
        val reportIssue = findViewById<LinearLayout?>(R.id.reportIssueSection)
        val healthFitness = findViewById<LinearLayout?>(R.id.healthFitnessSection)
        val drivingTips = findViewById<LinearLayout?>(R.id.drivingTipsSection)
        val checklist = findViewById<LinearLayout?>(R.id.checklistSection)

        // Emergency button card
        val emergencyButton = findViewById<MaterialCardView?>(R.id.emergencyButtonCard)

        // Settings icon (top-right)
        val settingsIcon = findViewById<ImageView?>(R.id.settingsIcon)

        // Set click listeners (safe null check)
        vehicleInspection?.setOnClickListener {
            navigateTo(VehicleInspection::class.java)
        }

        reportIssue?.setOnClickListener {
            navigateTo(ReportIssue::class.java)
        }

        healthFitness?.setOnClickListener {
            navigateTo(HealthFitness::class.java)
        }

        drivingTips?.setOnClickListener {
            navigateTo(DrivingTips::class.java)
        }

        checklist?.setOnClickListener {
            navigateTo(Checklist::class.java)
        }

        emergencyButton?.setOnClickListener {
            navigateTo(Emergency::class.java)
        }

        settingsIcon?.setOnClickListener {
            try {
                // Navigate to Settings activity in the same module
                val intent = Intent(this, LogisticsSettings::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                // Catch any crash and show toast
                Toast.makeText(this, "Unable to open Settings", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }


        // Debugging: warn if any views are missing
        if (vehicleInspection == null) logMissingView("vehicleInspectionSection")
        if (reportIssue == null) logMissingView("reportIssueSection")
        if (healthFitness == null) logMissingView("healthFitnessSection")
        if (drivingTips == null) logMissingView("drivingTipsSection")
        if (checklist == null) logMissingView("checklistSection")
        if (emergencyButton == null) logMissingView("emergencyButtonCard")
        if (settingsIcon == null) logMissingView("settingsIcon")
    }

    /**
     * Navigate to another activity safely
     */
    private fun navigateTo(activityClass: Class<*>) {
        try {
            val intent = Intent(this, activityClass)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open this feature", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    /**
     * Helper function to warn if a view wasn't found
     */
    private fun logMissingView(viewName: String) {
        println("⚠️ Warning: View with ID $viewName not found in layout")
    }
}