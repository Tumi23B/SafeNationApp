// This file defines the data model for a user's profile.
package com.example.safenationapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("id")
    val id: String,

    @SerialName("username")
    val username: String,

    @SerialName("sector")
    val sector: String,
)