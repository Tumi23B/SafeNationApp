// This file defines the data structure for an incident.
package com.example.agricultureagritech.features.incidentReporting.data.model

// This class holds the details for a single incident.
data class Incident(
    val priority: String,
    val id: String,
    val title: String,
    val timestamp: String,
    val owner: String,
    val status: String
)