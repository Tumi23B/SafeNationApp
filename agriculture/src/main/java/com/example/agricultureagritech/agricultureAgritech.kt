// This file defines the custom Application class for the agricultureAgritech sub-app.
package com.example.agricultureagritech

import android.app.Application

// The Application class for the agricultureAgritech module.
class agricultureAgritech : Application() {
    // This override is triggered when the application is first created.
    override fun onCreate() {
        super.onCreate()
        // Here you can initialize global components, such as Supabase.
    }
}