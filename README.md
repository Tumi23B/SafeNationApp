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
🪓 SafeNation – Mining Modular App
📱 Overview

The SafeNation app is a multi-industry safety and compliance mobile application designed to help users in high-risk sectors report incidents, complete checklists, and access training resources.
This module specifically focuses on the Mining industry, providing real-time safety data, weather-based risk alerts, compliance management tools, and offline support for remote operations.

🧩 Module Purpose

The Mining Modular App enhances occupational safety in mining environments by integrating:

Live weather tracking using the Open-Meteo API

Safety and compliance checklists

Incident reporting with photo and location support

Training content and quizzes

Dashboard and analytics view for miners and supervisors

⚙️ Core Features
Feature	Description
🧠 Safety Training	Access industry-specific training PDFs, videos, and quizzes.
✅ Compliance Checklists	Perform inspections and record risk assessments.
📷 Incident Reporting	Submit safety reports with geolocation and media uploads.
🌦️ Weather Integration	Real-time weather monitoring using Open-Meteo API.
📊 Dashboard	Displays user progress, safety stats, and incident logs.
⚙️ User Profile	Role-based access and editable profile settings.
📡 Offline Mode	Download training content for areas with poor connectivity.
🔔 Safety Notifications	Weather and incident alerts to improve situational awareness.
🌍 REST API Endpoints
Endpoint	Method	Description
/mining/content	GET	Fetch list of mining training materials (PDFs, videos).
/mining/content/{id}	GET	Retrieve a specific training item.
/mining/quiz/{contentId}	GET	Fetch quiz linked to specific training.
/mining/quiz/{contentId}/submit	POST	Submit quiz answers and receive result.
/mining/checklists	GET	Get compliance or inspection checklists.
/mining/incident-report	POST	Submit incident or safety report (with media upload).
/mining/dashboard	GET	Fetch user’s progress, scores, and logs.
/user/profile	PUT	Update user profile and preferences.

⚠️ Authentication and user management are centralized in the main SafeNation app.

☁️ API Integration

Primary API:
🔗 Open-Meteo Weather API

Used for real-time weather condition monitoring and safety alert generation.

Backend / Database:
🧩 Supabase
 — used for user data, progress tracking, and incident storage.

Deployment:

Cloud-based REST API (integrated with SafeNation backend)

Supabase for database synchronization

Android local storage for offline access

🧰 Technical Stack
Component	Technology
Platform	Android (Native)
Language	Kotlin
Architecture	MVVM (Modern Android Architecture Components)
API Client	Retrofit2 + Gson Converter
Location Services	Google Fused Location Provider API
Cloud Services	Supabase + Open-Meteo
Deployment	Google Play Store (Android)
🎬 Voice-Over Demonstration

▶️ Mining Module Voice-Over Video

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
