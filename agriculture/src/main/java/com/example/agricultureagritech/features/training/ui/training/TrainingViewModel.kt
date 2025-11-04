// ViewModel that exposes the tiles for the Training grid
package com.example.agricultureagritech.features.training.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.agricultureagritech.features.training.domain.TrainingTile
import com.safenation.agriculture.R

class TrainingViewModel(application: Application) : AndroidViewModel(application) {

    private val res = application.resources

    val tiles: List<TrainingTile> = listOf(
        TrainingTile(res.getString(R.string.training_fbm_title), R.drawable.fbm),
        TrainingTile(res.getString(R.string.training_pm_title), R.drawable.pro),
        TrainingTile(res.getString(R.string.training_fa_title), R.drawable.farmauto),
        TrainingTile(res.getString(R.string.training_pdm_title), R.drawable.pest)
    )
}