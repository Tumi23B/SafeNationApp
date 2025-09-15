// This file defines the REST API endpoints for the application.
package com.example.agricultureagritech.core.network

import com.example.agricultureagritech.core.domain.model.TrainingConcept
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/*
* This interface tells Retrofit how to construct the API requests.
* The 'suspend' keyword means the function can be paused and resumed, which is ideal for network calls.
*/
interface ApiService {
    @GET("training/{concept_key}")
    suspend fun getTrainingConcept(
        @Path("concept_key") conceptKey: String
    ): Response<TrainingConcept>
}