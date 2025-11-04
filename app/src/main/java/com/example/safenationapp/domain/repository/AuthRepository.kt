// This file contains the functions for handling user authentication.
package com.example.safenationapp.domain.repository

import android.util.Log
import com.example.safenationapp.data.model.Profile
import com.example.core.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.lang.Exception

class AuthRepository {

    /**
     * Signs in a user and retrieves their profile from the database.
     * The function gets the profile directly from the RPC call.
     */
    suspend fun signIn(email: String, password: String): Profile {
        try {
            // Attempt to sign in the user.
            SupabaseClient.client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            // Call the database function and get the profile in one step.
            return SupabaseClient.client.postgrest.rpc("create_missing_profile_on_login")
                .decodeSingle<Profile>()

        } catch (e: RestException) {
            // Catch specific Supabase errors and show a clearer message.
            Log.e("AuthRepository", "Sign-in failed with RestException", e)
            if (e.message == "Email not confirmed") {
                throw Exception("Open your Gmail app to confirm your email address, then log in.")
            }
            throw Exception(e.message ?: "Invalid email or password.")
        } catch (e: Exception) {
            // Catch any other exceptions.
            Log.e("AuthRepository", "Sign-in failed with generic exception", e)
            throw Exception(e.message ?: "An unexpected error occurred during sign-in.")
        }
    }

    /**
     * Signs up a new user with their profile data included in the user metadata.
     * This function returns null on success or a String with the error message if it fails.
     */
    suspend fun signUp(email: String, password: String, username: String, sector: String): String? {
        return try {
            // Sign up the user and pass the profile information in the 'data' field.
            SupabaseClient.client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                // Add the username and sector to the user's metadata.
                data = buildJsonObject {
                    put("username", username)
                    put("sector", sector)
                }
            }
            null // Return null to indicate success.
        } catch (e: RestException) {
            // Catch specific Supabase errors and return a user-friendly message.
            val errorMessage = "A database error occurred: ${e.message}"
            Log.e("AuthRepository", "Supabase RestException during sign-up", e)
            errorMessage
        } catch (e: Exception) {
            // Catch any other exceptions, like network issues.
            val errorMessage = "An unexpected error occurred: ${e.message}"
            Log.e("AuthRepository", "Generic exception during sign-up", e)
            errorMessage
        }
    }
}