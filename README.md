# Functionality of the REST API in the Agriculture & Agritech Modular App
For this modular app, a custom REST API was built using Node.js and deployed on RENDER.com

•	The API's primary function is to support the module's training screen.

•	It fetches educational materials from PDF documents and retrieves corresponding quizzes that were created using Google Forms. 

•	Both the training materials and the quizzes are loaded into a web viewer, which gives the user fast and direct access to the content.

# Agriculture & Agritech Modular App Links:

•	Agriculture & Agritech API URL: https://agri-safety.onrender.com/

•	API Deployment Site: https://render.com/

•	Agriculture & Agritech Voice Over App Video URL: https://www.dropbox.com/scl/fi/sujgyn4rnhlg5m4xk0tz0/Agriculture-sub-app.mp4?rlkey=6g4xwcrkqb4wa8wexut7b6uzo&st=9kd9cldp&dl=0


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
