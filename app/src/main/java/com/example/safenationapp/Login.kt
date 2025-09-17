// Handles the user login process and shows detailed error messages on the login screen.
package com.example.safenationapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.safenationapp.domain.repository.AuthRepository
import kotlinx.coroutines.launch

// Import dashboards for routing after login
import com.safenation.agriculture.features.dashboard.ui.DashboardActivity as AgricultureDashboard
import com.safenation.logistics.ui.LogisticsDashboard as LogisticsDashboard
import com.example.safenationapp.MainActivity as MiningDashboard

class Login : AppCompatActivity() {

    private val authRepository = AuthRepository()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Sets up system UI padding.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // UI references.
        val edtEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val edtPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.button2)

        // Handles Login button click and shows detailed errors.
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            when {
                email.isEmpty() -> edtEmail.error = "Please enter your email."
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    edtEmail.error = "Invalid email format."
                password.isEmpty() -> edtPassword.error = "Please enter your password."
                else -> {
                    lifecycleScope.launch {
                        try {
                            val profile = authRepository.signIn(email, password)
                            // Login success, route based on the user's sector.
                            when (profile.sector.lowercase()) {
                                "agriculture" -> {
                                    startActivity(Intent(this@Login, AgricultureDashboard::class.java))
                                    finish() // Prevent going back to the login screen.
                                }
                                "logistics" -> {
                                    startActivity(Intent(this@Login, LogisticsDashboard::class.java))
                                    finish() // Prevent going back to the login screen.
                                }
                                "mining" -> {
                                    startActivity(Intent(this@Login, MiningDashboard::class.java))
                                    finish() // Prevent going back to the login screen.
                                }
                                else -> {
                                    Toast.makeText(this@Login, "Login successful, but industry is unknown: ${profile.sector}", Toast.LENGTH_LONG).show()
                                }
                            }
                        } catch (e: Exception) {
                            // Handle all errors, including invalid credentials or missing profiles.
                            val errorMsg = e.message ?: "An unexpected error occurred."
                            Toast.makeText(this@Login, "Login failed: $errorMsg", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}