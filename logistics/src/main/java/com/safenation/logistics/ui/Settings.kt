package com.safenation.logistics.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.safenation.logistics.R

class Settings : AppCompatActivity() {

    private lateinit var languageGroup: RadioGroup
    private lateinit var rbEnglish: RadioButton
    private lateinit var rbSesotho: RadioButton
    private lateinit var rbIsiXhosa: RadioButton
    private lateinit var switchNotifications: Switch
    private lateinit var switchVehicleReminders: Switch
    private lateinit var switchEmergencyInfo: Switch
    private lateinit var saveButton: Button
    private lateinit var signOutButton: Button
    private lateinit var deleteAccountButton: Button

    private val prefsName = "DriverAppSettings"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements
        languageGroup = findViewById(R.id.languageRadioGroup)
        rbEnglish = findViewById(R.id.rbEnglish)
        rbSesotho = findViewById(R.id.rbSesotho)
        rbIsiXhosa = findViewById(R.id.rbIsiXhosa)
        switchNotifications = findViewById(R.id.switchNotifications)
        switchVehicleReminders = findViewById(R.id.switchVehicleReminders)
        switchEmergencyInfo = findViewById(R.id.switchEmergencyInfo)
        saveButton = findViewById(R.id.saveButton)
        signOutButton = findViewById(R.id.signOutButton)
        deleteAccountButton = findViewById(R.id.deleteAccountButton)

        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        // Load saved preferences
        loadSettings(prefs)

        // Save button listener
        saveButton.setOnClickListener { saveSettings(prefs) }

        // Sign out button listener
        signOutButton.setOnClickListener { showSignOutConfirmation() }

        // Delete account button listener
        deleteAccountButton.setOnClickListener { showDeleteAccountConfirmation() }
    }

    private fun loadSettings(prefs: android.content.SharedPreferences) {
        when (prefs.getString("language", "English")) {
            "English" -> rbEnglish.isChecked = true
            "Sesotho" -> rbSesotho.isChecked = true
            "IsiXhosa" -> rbIsiXhosa.isChecked = true
        }
        switchNotifications.isChecked = prefs.getBoolean("notifications", true)
        switchVehicleReminders.isChecked = prefs.getBoolean("vehicleReminders", true)
        switchEmergencyInfo.isChecked = prefs.getBoolean("emergencyInfo", true)
    }

    private fun saveSettings(prefs: android.content.SharedPreferences) {
        val selectedLanguage = when (languageGroup.checkedRadioButtonId) {
            R.id.rbEnglish -> "English"
            R.id.rbSesotho -> "Sesotho"
            R.id.rbIsiXhosa -> "IsiXhosa"
            else -> "English"
        }

        prefs.edit {
            putString("language", selectedLanguage)
            putBoolean("notifications", switchNotifications.isChecked)
            putBoolean("vehicleReminders", switchVehicleReminders.isChecked)
            putBoolean("emergencyInfo", switchEmergencyInfo.isChecked)
        }

        Toast.makeText(this, "Saving your settings...", Toast.LENGTH_SHORT).show()
        saveButton.postDelayed({
            Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show()
        }, 800)
    }

    private fun showSignOutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Sign Out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes") { _, _ -> signOut() }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Sign out cancelled", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showDeleteAccountConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ -> deleteAccount() }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Account deletion cancelled", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun signOut() {
        Toast.makeText(this, "Signing out...", Toast.LENGTH_SHORT).show()

        signOutButton.postDelayed({
            val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            prefs.edit {
                remove("authToken")
                remove("userId")
            }

            // Navigate to main app Login using Class.forName
            try {
                val intent = Intent(
                    this,
                    Class.forName("com.example.safenationapp.Login")
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Main app not found", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
        }, 1000)
    }

    private fun deleteAccount() {
        Toast.makeText(this, "Processing account deletion...", Toast.LENGTH_SHORT).show()

        deleteAccountButton.postDelayed({
            // Account deletion logic
            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()

            val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            prefs.edit {
                remove("authToken")
                remove("userId")
            }

            // Navigate to main app Register using Class.forName
            try {
                val intent = Intent(
                    this,
                    Class.forName("com.example.safenationapp.Register")
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Main app not found", Toast.LENGTH_SHORT).show()
            }

        }, 1500)
    }
}


