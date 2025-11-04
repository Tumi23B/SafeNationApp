package com.example.agricultureagritech.features.incidentReporting.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a single agriculture incident report.
 * This data class maps to the "agri_incidents" table in Supabase.
 */
@OptIn(ExperimentalSerializationApi::class) // <-- Add this annotation
@Serializable
data class AgriIncident(
    // Add the 'id' and 'created_at' fields, marking them as non-insertable
    // so Supabase uses the database-generated values.
    @SerialName("id")
    val id: Long? = null, // Auto-incrementing primary key

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
    val witnessContact: String? = null,

    // Add status to match the database schema
    @SerialName("status")
    val status: String,

    @SerialName("created_at")
    val createdAt: String? = null // Auto-generated timestamp
)