// This file acts as a data layer between the ViewModel and the network.
package com.example.agricultureagritech.features.training.domain.repository

import com.example.agricultureagritech.core.domain.model.TrainingConcept
import com.example.agricultureagritech.core.network.ApiService
import retrofit2.Response

/*
* The repository class abstracts the data source from the rest of the app.
* The ViewModel will use this repository to fetch data, without knowing if it comes from a network or a local database.
*/
class TrainingRepository(private val apiService: ApiService) {
    suspend fun getTrainingConcept(conceptKey: String): Response<TrainingConcept> {
        return apiService.getTrainingConcept(conceptKey)
    }
}