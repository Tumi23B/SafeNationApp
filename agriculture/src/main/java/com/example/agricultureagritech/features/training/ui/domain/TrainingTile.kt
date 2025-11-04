// Data class for the grid items on the training screen
package com.example.agricultureagritech.features.training.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TrainingTile(
    // The string resource ID for the title (e.g., R.string.training_fbm_title)
    @StringRes val titleResId: Int,

    // The drawable resource ID for the icon (e.g., R.drawable.fbm)
    @DrawableRes val iconResId: Int
)