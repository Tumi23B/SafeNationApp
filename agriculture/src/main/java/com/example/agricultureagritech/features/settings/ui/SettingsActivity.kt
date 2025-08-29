// This file hosts the SettingsFragment.
package com.safenation.agriculture.features.settings.ui

import android.os.Bundle
import androidx.fragment.app.commit
import com.agricultureAgritech.features.settings.ui.SettingsFragment
import com.example.safenationapp.BaseActivity

import com.safenation.agriculture.R

class SettingsActivity : BaseActivity() {
    /*
     * This function sets the layout and applies window insets.
     * It also loads the SettingsFragment.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        applyWindowInsets(findViewById(R.id.container))

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container, SettingsFragment())
            }
        }
    }
}