# Feature that was used from part 1 in the agriculture & agritech modular app:
* For this modular app we used the layout design of the home dashboard of the research app that we specified in part 1. The app was named SiteApp.

# Functionality of the REST API in the Agriculture & Agritech Modular App
For this modular app, a custom REST API was built using Node.js and deployed on RENDER.com

•	The API's primary function is to support the module's training screen.

•	It fetches educational materials from PDF documents and retrieves corresponding quizzes that were created using Google Forms. 

•	Both the training materials and the quizzes are loaded into a web viewer, which gives the user fast and direct access to the content.

# Agriculture & Agritech Modular App Links:

•	Agriculture & Agritech API URL: https://agri-safety.onrender.com/

•	API Deployment Site: https://render.com/

•	Agriculture & Agritech Voice Over App Video URL: https://www.dropbox.com/scl/fi/sujgyn4rnhlg5m4xk0tz0/Agriculture-sub-app.mp4?rlkey=6g4xwcrkqb4wa8wexut7b6uzo&st=9kd9cldp&dl=0

---
# 🪓 SafeNation – Mining Modular App

## 📱 Overview
The **SafeNation** app is a multi-industry safety and compliance mobile application designed to help users in high-risk sectors report incidents, complete checklists, and access training resources.  
This module specifically focuses on the **Mining industry**, providing real-time safety data, weather-based risk alerts, compliance management tools, and offline support for remote operations.

---

## 🧩 Module Purpose
The **Mining Modular App** enhances occupational safety in mining environments by integrating:

- 🌦️ Live weather tracking using the **Open-Meteo API**  
- ✅ Safety and compliance checklists  
- 📷 Incident reporting with photo and location support  
- 🧠 Training content and quizzes  
- 📊 Dashboard and analytics view for miners and supervisors  

---

## ⚙️ Core Features

| Feature | Description |
|----------|--------------|
| 🧠 **Safety Training** | Access industry-specific training PDFs, videos, and quizzes. |
| ✅ **Compliance Checklists** | Perform inspections and record risk assessments. |
| 📷 **Incident Reporting** | Submit safety reports with geolocation and media uploads. |
| 🌦️ **Weather Integration** | Real-time weather monitoring using Open-Meteo API. |
| 📊 **Dashboard** | Displays user progress, safety stats, and incident logs. |
| ⚙️ **User Profile** | Role-based access and editable profile settings. |
| 📡 **Offline Mode** | Download training content for areas with poor connectivity. |
| 🔔 **Safety Notifications** | Weather and incident alerts to improve situational awareness. |

---

## 🌍 REST API Endpoints

| Endpoint | Method | Description |
|-----------|--------|-------------|
| `/mining/content` | GET | Fetch list of mining training materials (PDFs, videos). |
| `/mining/content/{id}` | GET | Retrieve a specific training item. |
| `/mining/quiz/{contentId}` | GET | Fetch quiz linked to specific training. |
| `/mining/quiz/{contentId}/submit` | POST | Submit quiz answers and receive result. |
| `/mining/checklists` | GET | Get compliance or inspection checklists. |
| `/mining/incident-report` | POST | Submit incident or safety report (with media upload). |
| `/mining/dashboard` | GET | Fetch user’s progress, scores, and logs. |
| `/user/profile` | PUT | Update user profile and preferences. |

> ⚠️ **Authentication and user management are centralized in the main SafeNation app.**

---

## ☁️ API Integration

**Primary API:**  
🔗 [Open-Meteo Weather API](https://api.open-meteo.com/)  
Used for real-time weather condition monitoring and safety alert generation.

**Backend / Database:**  
🧩 [Supabase](https://supabase.com/) — used for user data, progress tracking, and incident storage.

**Deployment:**
- Cloud-based REST API (integrated with SafeNation backend)  
- Supabase for database synchronization  
- Android local storage for offline access  

---

## 🧰 Technical Stack

| Component | Technology |
|------------|-------------|
| **Platform** | Android (Native) |
| **Language** | Kotlin |
| **Architecture** | MVVM (Modern Android Architecture Components) |
| **API Client** | Retrofit2 + Gson Converter |
| **Location Services** | Google Fused Location Provider API |
| **Cloud Services** | Supabase + Open-Meteo |
| **Deployment** | Google Play Store (Android) |

---

## 🎬 Voice-Over Demonstration
▶️ [Mining Module Voice-Over Video](https://youtube.com/shorts/KO2KXpqkyuw?feature=share)
---
# How we integrated the Modular Apps into the Main App

Guide to explain how we add new features as separate library modules to the main application. This method helps us keep the codebase clean, organized, and easier to maintain. We used Supabase as our database.

For the purpose of this guide, we used the **agriculture module** as an example!

### 1. Create the New Module

First, you need to create a new Android Library module.

*   In Android Studio, go to `File > New > New Module...`.
*   Choose `No Activity` from the list.
*   Give your module a name, for example, `agriculture`.
*   Click `Finish`. Android Studio will create the module with a standard directory structure.

### 2. Connect the Module to the Main App

Next, you must make the main app aware of the new module.

*   Check your `settings.gradle.kts` file at the root of the project. Android Studio should have automatically added your module. If not, add it yourself.
Here is an example:
    ```
    // In settings.gradle.kts
    include(":app", ":agriculture")
    ```

*   Now, open the `build.gradle.kts` file for the main `:app` module. Add the new module as a dependency. This allows the app to access the module's code.
Here is an example:
    ```
    // In app/build.gradle.kts
    dependencies {
        implementation(project(":agriculture"))
        // other dependencies...
    }
    ```

### 3. Configure the Module's Manifest

The main application needs to know which activities exist inside your new module. You must declare every activity in the module's own `AndroidManifest.xml` file.

*   Open the manifest file located at `agriculture/src/main/AndroidManifest.xml`.
*   Declare each activity with its full package name.
Here is an example:
    ```
    <!-- In agriculture/src/main/AndroidManifest.xml -->
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android">
        <application>
            <activity
                android:name="com.safenation.agriculture.features.dashboard.ui.DashboardActivity"
                android:exported="false" />
            <activity
                android:name="com.safenation.agriculture.features.settings.ui.SettingsActivity"
                android:exported="false" />
        </application>
    </manifest>
    ```

### 4. Navigate from the Main App to the Module

To open a screen from the new module, you use a standard `Intent`.

*   From any activity in your main `:app` module, create an `Intent` that points to the activity in the `agriculture` module.
*   The `Intent` needs the full package and class name of the destination activity.
Here is an example:
    ```
    // In a file within the :app module, like Welcome.kt
    import com.safenation.agriculture.features.dashboard.ui.DashboardActivity

    val agricultureButton = findViewById<Button>(R.id.agriculture_button)
    agricultureButton.setOnClickListener {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
    }
    ```

By following these steps, we successfully integrate a new feature module. This keeps our project scalable and separates concerns effectively.

🚛 SafeNation – Logistics Modular App
📱 Overview

As part of the SafeNation safety and compliance mobile application development team, I was responsible for the Logistics module.
SafeNation enables users across various industries to report safety incidents and utilize environment-specific checklists. Our team focused on three core sectors: Logistics, Mining, and Agriculture & Agritech.
The app implements role-based access control, delivering customized module experiences based on the industry selected during user registration.

🧩 Module Purpose

The Logistics module improves road transport and delivery operations by providing real-time safety alerts, environmental monitoring, and access to emergency resources.
⚙️ REST API Functionality
The Logistics module integrates multiple REST APIs to deliver real-time data for safety, navigation, and weather conditions.
These APIs work together to ensure that drivers and logistics operators have up-to-date operational insights.
Core API Integrations
Open-Meteo Weather API
Base URL: https://api.open-meteo.com/
Purpose: Provides real-time weather data (temperature, wind speed, weather codes) for route safety and driving condition monitoring.
Overpass API (OpenStreetMap)
Base URL: https://overpass-api.de/api/
Purpose: Identifies nearby safety locations (police stations, hospitals, fuel stations, fire stations, etc.).
BigDataCloud Geocoding API
Base URL: https://api.bigdatacloud.net/
Purpose: Converts GPS coordinates into readable addresses to improve situational awareness.
Google Maps Navigation Integration
Functionality: Turn-by-turn navigation via deep linking.
Purpose: Enables direct routing to safety or emergency facilities.

🌍 Module Features & Capabilities
Feature	Description
🚨 Real-Time Safety Monitoring	Tracks location and weather to alert drivers about hazardous conditions.
🏥 Safety Infrastructure Integration	Displays nearby police, hospitals, fuel, and fire stations.
📡 Emergency Service Coordination	Allows quick navigation to emergency services through Google Maps.
🌦️ Weather Condition Alerts	Sends notifications based on weather risks.
🛣️ Route Risk Assessment	Evaluates routes for environmental and safety risks.


🧰 Technical Implementation
Component	Details
Platform	Android (Native)
Language	Kotlin
Architecture	Modern Android Architecture Components
API Integration	Retrofit2 with Gson Converter
Location Services	Google Fused Location Provider API
Authentication	Role-based access from main SafeNation app

☁️ Deployment & Infrastructure
APIs Used: Open-Meteo, Overpass, BigDataCloud
Navigation: Google Maps Deep Linking
Data Storage: Local device + cloud integration
Deployment: Android app to be  published to Google Play Store

🚛 Logistics Module Impact

The Logistics module addresses key challenges in transportation safety by enabling:
Real-time hazard detection
Quick access to emergency services
Optimized route safety
Proactive risk mitigation
Enhanced driver safety awareness

📎 Application Information

Main Application GitHub URL: https://github.com/Tumi23B/SafeNationApp
Voiceover Demonstration: https://youtu.be/HhkA50OyclM
Application Framework: Multi-module, industry-specific safety platform
Target Industries: Logistics, Mining, Agriculture & Agritech
Technology Stack: Android Native, REST APIs, Cloud Services

🏁 Conclusion
The Logistics module is a critical component of the SafeNation application, combining real-time environmental monitoring and intelligent routing to improve safety awareness and emergency responsiveness in logistics operations.
