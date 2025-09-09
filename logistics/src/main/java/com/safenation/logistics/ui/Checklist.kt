package com.safenation.logistics.ui

import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.safenation.logistics.R
class Checklist : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checklist)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val checkFuel = findViewById<CheckBox>(R.id.checkFuel)
        val checkLights = findViewById<CheckBox>(R.id.checkLights)
        val checkTires = findViewById<CheckBox>(R.id.checkTires)
        val checkBrakes = findViewById<CheckBox>(R.id.checkBrakes)
        val checkMirrors = findViewById<CheckBox>(R.id.checkMirrors)
        val checkLoad = findViewById<CheckBox>(R.id.checkLoad)
        val submitButton = findViewById<MaterialButton>(R.id.submitChecklistButton)

        submitButton.setOnClickListener {
            val completedItems = mutableListOf<String>()
            if (checkFuel.isChecked) completedItems.add("Fuel checked")
            if (checkLights.isChecked) completedItems.add("Lights checked")
            if (checkTires.isChecked) completedItems.add("Tires inspected")
            if (checkBrakes.isChecked) completedItems.add("Brakes tested")
            if (checkMirrors.isChecked) completedItems.add("Mirrors adjusted")
            if (checkLoad.isChecked) completedItems.add("Load secured")

            val message = if (completedItems.isEmpty()) {
                "No checklist items completed."
            } else {
                "Completed: ${completedItems.joinToString(", ")}"
            }

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}