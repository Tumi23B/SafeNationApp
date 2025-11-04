// This file configures the build for the core module, which holds shared code.
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

android {
    namespace = "com.example.safenationapp.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // These are the dependencies required by the shared BaseActivity.
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Supabase (Moved from :app to :core)
    // Use 'api' to expose these libraries to other modules
    api(platform("io.github.jan-tennert.supabase:bom:2.5.2"))
    api("io.github.jan-tennert.supabase:gotrue-kt")
    api("io.github.jan-tennert.supabase:postgrest-kt")
    api("io.github.jan-tennert.supabase:functions-kt")

    // Ktor and Serialization must also use 'api'
    api("io.ktor:ktor-client-android:2.3.11")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}