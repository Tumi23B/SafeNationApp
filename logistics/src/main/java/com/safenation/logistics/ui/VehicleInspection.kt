package com.safenation.logistics.ui

import android.os.Bundle
import android.os.Handler
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.safenation.logistics.R
import com.google.android.material.textfield.TextInputEditText

class VehicleInspection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vehicle_inspection)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to UI elements
        val vinInput = findViewById<TextInputEditText>(R.id.vinInput)
        val licensePlateInput = findViewById<TextInputEditText>(R.id.licensePlateInput)
        val submitButton = findViewById<MaterialButton>(R.id.submitButton)

        // Get all checkboxes
        val checkTires = findViewById<CheckBox>(R.id.checkTires)
        val checkLights = findViewById<CheckBox>(R.id.checkLights)
        val checkBody = findViewById<CheckBox>(R.id.checkBody)
        val checkWindows = findViewById<CheckBox>(R.id.checkWindows)
        val checkSeats = findViewById<CheckBox>(R.id.checkSeats)
        val checkDashboard = findViewById<CheckBox>(R.id.checkDashboard)
        val checkControls = findViewById<CheckBox>(R.id.checkControls)
        val checkBrakes = findViewById<CheckBox>(R.id.checkBrakes)
        val checkEngine = findViewById<CheckBox>(R.id.checkEngine)
        val checkTransmission = findViewById<CheckBox>(R.id.checkTransmission)
        val notesInput = findViewById<TextInputEditText>(R.id.notesInput)

        // Set up submit button click listener
        submitButton.setOnClickListener {
            if (validateForm()) {
                submitInspection()
            }
        }
    }

    // Validate form inputs
    private fun validateForm(): Boolean {
        val vin = findViewById<TextInputEditText>(R.id.vinInput).text.toString().trim()
        val licensePlate = findViewById<TextInputEditText>(R.id.licensePlateInput).text.toString().trim()

        if (vin.isEmpty()) {
            findViewById<TextInputEditText>(R.id.vinInput).error = "Please enter VIN number"
            return false
        }

        if (licensePlate.isEmpty()) {
            findViewById<TextInputEditText>(R.id.licensePlateInput).error = "Please enter license plate"
            return false
        }

        // Check if at least one inspection item is checked
        val hasCheckedItem = listOf(
            R.id.checkTires, R.id.checkLights, R.id.checkBody, R.id.checkWindows,
            R.id.checkSeats, R.id.checkDashboard, R.id.checkControls,
            R.id.checkBrakes, R.id.checkEngine, R.id.checkTransmission
        ).any { findViewById<CheckBox>(it).isChecked }

        if (!hasCheckedItem) {
            Toast.makeText(this, "Please check at least one inspection item", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // Submit the inspection
    private fun submitInspection() {
        val vin = findViewById<TextInputEditText>(R.id.vinInput).text.toString().trim()
        val licensePlate = findViewById<TextInputEditText>(R.id.licensePlateInput).text.toString().trim()
        val notes = findViewById<TextInputEditText>(R.id.notesInput).text.toString().trim()

        // Get all checkbox states
        val inspectionResults = mapOf(
            "Tires" to findViewById<CheckBox>(R.id.checkTires).isChecked,
            "Lights" to findViewById<CheckBox>(R.id.checkLights).isChecked,
            "Body" to findViewById<CheckBox>(R.id.checkBody).isChecked,
            "Windows" to findViewById<CheckBox>(R.id.checkWindows).isChecked,
            "Seats" to findViewById<CheckBox>(R.id.checkSeats).isChecked,
            "Dashboard" to findViewById<CheckBox>(R.id.checkDashboard).isChecked,
            "Controls" to findViewById<CheckBox>(R.id.checkControls).isChecked,
            "Brakes" to findViewById<CheckBox>(R.id.checkBrakes).isChecked,
            "Engine" to findViewById<CheckBox>(R.id.checkEngine).isChecked,
            "Transmission" to findViewById<CheckBox>(R.id.checkTransmission).isChecked
        )

        // Count passed items
        val passedCount = inspectionResults.values.count { it }
        val totalCount = inspectionResults.size

        // Simulate processing
        Toast.makeText(this, "Submitting inspection...", Toast.LENGTH_SHORT).show()


        // 1. Save the inspection data to a database
        // 2. Upload to your server
        // 3. Generate a report

        // Simulate a delay for network request
        Handler().postDelayed({
            val message = "Inspection submitted! $passedCount/$totalCount items passed"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            // Optionally navigate back or clear form
            // finish() // Uncomment to close activity after submission

        }, 1500)
    }
}

