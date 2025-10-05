package com.safenation.logistics.ui

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.safenation.logistics.R
import com.safenation.logistics.ui.Supabase
class LogisticsSettings : AppCompatActivity() {

    private val prefsName = "DriverAppSettings"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_logistics_settings)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize all views
        val languageGroup = findViewById<RadioGroup>(R.id.languageRadioGroup)
        val rbEnglish = findViewById<RadioButton>(R.id.rbEnglish)
        val rbSesotho = findViewById<RadioButton>(R.id.rbSesotho)
        val rbIsiXhosa = findViewById<RadioButton>(R.id.rbIsiXhosa)
        val switchNotifications = findViewById<Switch>(R.id.switchNotifications)
        val switchVehicleReminders = findViewById<Switch>(R.id.switchVehicleReminders)
        val switchEmergencyInfo = findViewById<Switch>(R.id.switchEmergencyInfo)
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val signOutButton = findViewById<Button>(R.id.signOutButton)
        val deleteAccountButton = findViewById<Button>(R.id.deleteAccountButton)

        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        // Load saved preferences
        loadSettings(prefs, languageGroup, rbEnglish, rbSesotho, rbIsiXhosa,
            switchNotifications, switchVehicleReminders, switchEmergencyInfo, switchDarkMode)

        // Save settings button
        saveButton.setOnClickListener {
            saveSettings(prefs, languageGroup, switchNotifications, switchVehicleReminders, switchEmergencyInfo, switchDarkMode)
        }

        // Sign out button
        signOutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Do you want to sign out?")
                .setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show()
                    // Only simulate logout, don't finish immediately if you want to test
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Delete account button
        deleteAccountButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure? Cannot undo.")
                .setPositiveButton("Delete") { _, _ ->
                    Toast.makeText(this, "Account deleted!", Toast.LENGTH_SHORT).show()
                    // Only simulate delete, no auto finish
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun loadSettings(
        prefs: android.content.SharedPreferences,
        languageGroup: RadioGroup,
        rbEnglish: RadioButton,
        rbSesotho: RadioButton,
        rbIsiXhosa: RadioButton,
        switchNotifications: Switch,
        switchVehicleReminders: Switch,
        switchEmergencyInfo: Switch,
        switchDarkMode: Switch
    ) {
        when (prefs.getString("language", "English")) {
            "English" -> rbEnglish.isChecked = true
            "Sesotho" -> rbSesotho.isChecked = true
            "IsiXhosa" -> rbIsiXhosa.isChecked = true
        }
        switchNotifications.isChecked = prefs.getBoolean("notifications", true)
        switchVehicleReminders.isChecked = prefs.getBoolean("vehicleReminders", true)
        switchEmergencyInfo.isChecked = prefs.getBoolean("emergencyInfo", true)
        switchDarkMode.isChecked = prefs.getBoolean("darkMode", false)
    }

    private fun saveSettings(
        prefs: android.content.SharedPreferences,
        languageGroup: RadioGroup,
        switchNotifications: Switch,
        switchVehicleReminders: Switch,
        switchEmergencyInfo: Switch,
        switchDarkMode: Switch
    ) {
        val language = when(languageGroup.checkedRadioButtonId) {
            R.id.rbEnglish -> "English"
            R.id.rbSesotho -> "Sesotho"
            R.id.rbIsiXhosa -> "IsiXhosa"
            else -> "English"
        }

        prefs.edit {
            putString("language", language)
            putBoolean("notifications", switchNotifications.isChecked)
            putBoolean("vehicleReminders", switchVehicleReminders.isChecked)
            putBoolean("emergencyInfo", switchEmergencyInfo.isChecked)
            putBoolean("darkMode", switchDarkMode.isChecked)
        }

        Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show()
    }
}
