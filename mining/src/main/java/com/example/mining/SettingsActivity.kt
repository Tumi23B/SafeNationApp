package com.example.mining

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.safenationapp.BaseActivity

class SettingsActivity : BaseActivity() {
    /*
     * This function sets the layout and applies window insets.
     * It also loads the SettingFragment.
     */
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.safenation.mining.R.layout.activity_settings)
        applyWindowInsets(findViewById(com.safenation.mining.R.id.container))

        }

}