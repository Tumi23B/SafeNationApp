package com.example.safenationapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Register : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // System bars padding 
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtName = findViewById<EditText>(R.id.editTextText)
        val edtEmail = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val spinnerIndustry = findViewById<Spinner>(R.id.spinnerIndustry)
        val edtPassword = findViewById<EditText>(R.id.editTextTextPassword2)
        val edtConfirmPassword = findViewById<EditText>(R.id.editTextTextPassword3)
        val btnRegister = findViewById<Button>(R.id.button4)

        // Spinner setup 
        val industries = arrayOf("Select Sector", "Agriculture", "Mining", "Logistics")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, industries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIndustry.adapter = adapter

        spinnerIndustry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                Log.d("RegisterActivity", "Spinner selected: $position - ${parent.getItemAtPosition(position)}")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Register button click 
        btnRegister.setOnClickListener {
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()
            val confirmPassword = edtConfirmPassword.text.toString()
            val selectedIndustry = spinnerIndustry.selectedItem.toString()

            // Validation 
            when {
                name.isEmpty() -> edtName.error = "Please enter your name"
                email.isEmpty() -> edtEmail.error = "Please enter your email"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> edtEmail.error = "Invalid email"
                selectedIndustry == "Select Industry" -> Toast.makeText(this, "Please select an industry", Toast.LENGTH_SHORT).show()
                password.isEmpty() -> edtPassword.error = "Please enter a password"
                confirmPassword.isEmpty() -> edtConfirmPassword.error = "Please confirm your password"
                password != confirmPassword -> edtConfirmPassword.error = "Passwords do not match"

                else -> {
                    // Save user details 
                    val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("name", name)
                        putString("email", email)
                        putString("password", password)
                        putString("industry", selectedIndustry)
                        apply()
                    }

                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()


                    // Navigate to the selected dashboard 
                    try {
                        val intent = when (selectedIndustry) {
                            "Agriculture" -> Intent(this, com.safenation.agriculture.features.dashboard.ui.DashboardActivity::class.java)
                            "Mining" -> Intent(this, com.example.safenationapp.MainActivity::class.java)
                            "Logistics" -> Intent(this@Register, com.safenation.logistics.ui.LogisticsDashboard::class.java)
                            else -> null
                        }

                        if (intent != null) {
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Cannot navigate. Please try again.", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        Log.e("RegisterActivity", "Navigation error: ${e.message}")
                        Toast.makeText(this, "Navigation failed. Check dashboard setup.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}


