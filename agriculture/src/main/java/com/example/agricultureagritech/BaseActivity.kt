// This file defines a base activity for handling common screen setup.
package com.example.safenationapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// This abstract class provides common functionality for setting up edge-to-edge display and handling window insets.
abstract class BaseActivity : AppCompatActivity() {

    // Function enables edge-to-edge display and sets up a listener to apply padding based on the system bars.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    //It ensures that the content is not obscured by the system bars.
    protected fun applyWindowInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}