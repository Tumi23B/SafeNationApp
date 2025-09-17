// This file initializes and provides a singleton Supabase client.
package com.example.safenationapp.data.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    /*
    * Initializes the Supabase client with our project's public anon key.
    * This key is safe to use in a mobile app and works with your security rules.
    */
    val client = createSupabaseClient(
        supabaseUrl = "https://reunnxlrcdivzhycrhvl.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJldW5ueGxyY2RpdnpoeWNyaHZsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTU5NTc3OTUsImV4cCI6MjA3MTUzMzc5NX0.JDNqdEmi9rz9viEIsIMuxHi-GXdI0Qqpa7nQNGgAeVg"
    ) {
        install(Auth)
        install(Postgrest)
        install(Functions)
    }
}