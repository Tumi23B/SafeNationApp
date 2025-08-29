// This file hosts the TrainingFragment and manages the screen entry point.
package com.safenation.agriculture.features.training.ui

import android.os.Bundle
import com.example.agricultureagritech.features.training.ui.TrainingFragment
import com.example.safenationapp.BaseActivity
import com.safenation.agriculture.R

class TrainingModuleActivity : BaseActivity() {
    /*
     * This function sets the layout and applies window insets.
     * It also loads the TrainingFragment.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        applyWindowInsets(findViewById(R.id.container))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TrainingFragment.newInstance())
                .commit()
        }
    }
}