// This file contains the logic for the application's welcome screen.
package com.example.safenationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.mining.MainActivity
import com.safenation.agriculture.features.dashboard.ui.DashboardActivity

class Welcome : BaseActivity() {
    //This function sets up the view and the button's click listener.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        applyWindowInsets(findViewById(R.id.main))

        val agricultureButton = findViewById<Button>(R.id.agriculture_button)
        agricultureButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }


        val miningButton = findViewById<Button>(R.id.mining_button)
        agricultureButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}