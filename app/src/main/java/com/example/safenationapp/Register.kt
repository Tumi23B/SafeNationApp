package com.example.safenationapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.safenationapp.domain.repository.AuthRepository
import kotlinx.coroutines.launch
import java.util.Locale

class Register : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var sectorSpinner: Spinner
    private lateinit var registerButton: Button
    private lateinit var progressBar: ProgressBar

    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        // Declaring UI elements using the correct IDs from the layout file activity_register.xml

        usernameEditText = findViewById(R.id.editTextText)
        emailEditText = findViewById(R.id.editTextTextEmailAddress2)
        passwordEditText = findViewById(R.id.editTextTextPassword2)
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword3)
        sectorSpinner = findViewById(R.id.spinnerIndustry)
        registerButton = findViewById(R.id.button4)
        progressBar = findViewById(R.id.progressBar)

        // the drop down menu set up with the sector choices.
        ArrayAdapter.createFromResource(
            this,
            R.array.sector_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sectorSpinner.adapter = adapter
        }

        // click listener for the register button.
        registerButton.setOnClickListener {
            handleRegistration()
        }
    }

    // This function gets user input, validates it, and shows detailed error messages on failure.
    private fun handleRegistration() {
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        // Converts the selected sector to lowercase to match the database enum.
        val sector = sectorSpinner.selectedItem.toString().lowercase(Locale.getDefault())

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        setLoading(true)

        lifecycleScope.launch {
            val errorMessage = authRepository.signUp(email, password, username, sector)
            if (errorMessage == null) {
                Toast.makeText(this@Register, "Registration successful!", Toast.LENGTH_LONG).show()
                finish() // Close the registration screen.
            } else {
                // Display the specific error message from the repository file under domain folder.
                Toast.makeText(this@Register, errorMessage, Toast.LENGTH_LONG).show()
            }
            setLoading(false)
        }
    }

    // This function disables the button while the app communicates with the database.
    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            registerButton.isEnabled = false
        } else {
            progressBar.visibility = View.GONE
            registerButton.isEnabled = true
        }
    }
}