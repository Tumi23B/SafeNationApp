// ViewModel that exposes the tiles for the Training grid
package com.example.agricultureagritech.features.training.ui

import androidx.lifecycle.ViewModel
import com.example.agricultureagritech.features.training.domain.TrainingTile
import com.safenation.agriculture.R

class TrainingViewModel : ViewModel() {
    val tiles: List<TrainingTile> = listOf(
        TrainingTile("Farm Business Management", R.drawable.fbm),
        TrainingTile("Production Management", R.drawable.pro),
        TrainingTile("Farm Automation", R.drawable.farmauto),
        TrainingTile("Pest and disease management", R.drawable.pest)
    )
}