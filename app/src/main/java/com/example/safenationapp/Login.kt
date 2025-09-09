package com.example.safenationapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Import dashboards 
import com.safenation.agriculture.features.dashboard.ui.DashboardActivity as AgricultureDashboard
import com.safenation.logistics.ui.LogisticsDashboard as LogisticsDashboard

class Login : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Handle system UI padding 
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // UI references 
        val edtEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val edtPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.button2)
        // Load saved credentials 
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Handle Login button 
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            val savedEmail = sharedPref.getString("email", null)
            val savedPassword = sharedPref.getString("password", null)
            val savedIndustry = sharedPref.getString("industry", null)

            when {
                email.isEmpty() -> edtEmail.error = "Please enter your email"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    edtEmail.error = "Invalid email format"
                password.isEmpty() -> edtPassword.error = "Please enter your password"
                savedEmail == null || savedPassword == null || savedIndustry == null ->
                    Toast.makeText(this, "No account found, please register", Toast.LENGTH_SHORT).show()
                email != savedEmail || password != savedPassword ->
                    Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                else -> {
                    // ✅ Login success → route based on industry 
                    when (savedIndustry) {
                        "Agriculture" -> startActivity(Intent(this, AgricultureDashboard::class.java))
                       // "Mining" -> startActivity(Intent(this, MiningDashboard::class.java))
                        "Logistics" -> startActivity(Intent(this, LogisticsDashboard::class.java))
                        else -> Toast.makeText(this, "Unknown industry selected", Toast.LENGTH_SHORT).show()
                    }
                    finish() // Prevent going back to login screen 
                }
            }
        }

    }
}




