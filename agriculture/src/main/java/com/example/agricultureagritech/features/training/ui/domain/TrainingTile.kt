// Model for a Training tile item
package com.example.agricultureagritech.features.training.domain

import androidx.annotation.DrawableRes

data class TrainingTile(
    val title: String,
    @DrawableRes val iconRes: Int
)