package com.safenation.logistics.ui

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object Supabase {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://reunnxlrcdivzhycrhvl.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJldW5ueGxyY2RpdnpoeWNyaHZsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTU5NTc3OTUsImV4cCI6MjA3MTUzMzc5NX0.JDNqdEmi9rz9viEIsIMuxHi-GXdI0Qqpa7nQNGgAeVg"
    ) {
        // Install the module needed for logistics
        install(Postgrest)

    }
}