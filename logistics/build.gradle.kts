// This file manages the dependencies for the logistics module.
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.safenation.logistics"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Add build config fields if needed for API keys
        buildConfigField("String", "SUPABASE_URL", "\"https://reunnxlrcdivzhycrhvl.supabase.co\"")
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJldW5ueGxyY2RpdnpoeWNyaHZsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTU5NTc3OTUsImV4cCI6MjA3MTUzMzc5NX0.JDNqdEmi9rz9viEIsIMuxHi-GXdI0Qqpa7nQNGgAeVg\"")
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true // Enable build config
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // Enable debug build features if needed
            isTestCoverageEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    // Add packaging options to avoid conflicts
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // This module depends on the core module for shared code like BaseActivity.
    implementation(project(":core"))

    // AndroidX Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.fragment:fragment-ktx:1.7.1")

    // Navigation (if needed in this module)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Location Services for device location access
    implementation("com.google.android.gms:play-services-location:21.2.0") // Updated version

    // Coroutines for asynchronous programming
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Updated version
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // Updated version

    // Network Libraries for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Updated version

    // Supabase for database operations - Using BOM for consistent versions
    implementation(platform("io.github.jan-tennert.supabase:bom:2.5.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    // Add if you need authentication in this module
    // implementation("io.github.jan-tennert.supabase:gotrue-kt")

    // Kotlinx Serialization (if needed for Supabase)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Image loading for safety feature maps
    implementation("io.coil-kt:coil:2.5.0") // Updated version

    // Testing Dependencies
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") // For coroutine testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Updated version
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Updated version
}