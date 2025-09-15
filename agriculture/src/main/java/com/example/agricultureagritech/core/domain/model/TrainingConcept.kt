// This file defines the data structure for a training concept.
package com.example.agricultureagritech.core.domain.model

import com.google.gson.annotations.SerializedName

/*
* This data class maps the JSON response from your API to a Kotlin object.
* The SerializedName annotation links the JSON key to the class property.
*/
data class TrainingConcept(
    @SerializedName("concept_name")
    val conceptName: String,

    @SerializedName("learning_material_url")
    val learningMaterialUrl: String,

    @SerializedName("quiz_url")
    val quizUrl: String
)