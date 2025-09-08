// This file contains the logic for the application's welcome screen.
package com.example.safenationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.safenation.agriculture.features.dashboard.ui.DashboardActivity

class Welcome : BaseActivity() {
    //This function sets up the view and the button's click listener.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val agricultureButton = findViewById<Button>(R.id.agriculture_button)
        agricultureButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }


        val miningButton = findViewById<Button>(R.id.mining_button)
        miningButton.setOnClickListener {
            // Use the correct MainActivity from THIS module
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}