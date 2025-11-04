package com.example.agricultureagritech.features.incidentReporting.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a single agriculture incident report.
 * This data class maps to the "agri_incidents" table in Supabase.
 */
@Serializable
data class AgriIncident(
    @SerialName("incident_type")
    val incidentType: String,

    @SerialName("date")
    val date: String, // Stored as string, matching date picker output

    @SerialName("time")
    val time: String, // Stored as string, matching time picker output

    @SerialName("location")
    val location: String,

    @SerialName("description")
    val description: String?,

    @SerialName("severity")
    val severity: String,

    @SerialName("witness_name")
    val witnessName: String? = null,

    @SerialName("witness_contact")
    val witnessContact: String? = null
)