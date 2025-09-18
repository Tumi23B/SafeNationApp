// This file defines the main activity of the application, which handles the primary user interface and navigation.
package com.safenation.mining

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.safenationapp.BaseActivity
import com.safenation.mining.databinding.ActivityMainBinding
import org.osmdroid.config.Configuration

class MainActivity : BaseActivity() {
    /**
     * These variables handle the view binding, navigation controller, and app bar configuration.
     */
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    /**
     * This function initializes the activity, sets up the view, and configures navigation.
     * It also applies window insets to ensure content is not hidden by system bars.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyWindowInsets(binding.root)
        setupNavigation()

        // Initialize osmdroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        Configuration.getInstance().userAgentValue = packageName
    }

    /**
     * This function sets up the navigation components, including the NavController, AppBar, NavigationView, and BottomNavigationView.
     */
    private fun setupNavigation() {
        // Get NavHostFragment and NavController using the local R file
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup AppBarConfiguration with top-level destinations from the local R file
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.incidentReportFragment,
                R.id.safetyFragment,
                R.id.trainingFragment,
                R.id.checklistFragment,
                R.id.settingsFragment
            ),
            binding.drawerLayout
        )

        // Setup ActionBar with NavController
        binding.toolbar?.setupWithNavController(navController, appBarConfiguration)
        // Setup NavigationView with NavController
        binding.navigationView.setupWithNavController(navController)

        // Setup BottomNavigationView with NavController
        binding.bottomNavigation.setupWithNavController(navController)

        // Handle navigation item selection for custom behavior
        setupNavigationListeners()
    }

    /**
     * This function configures listeners for handling clicks on navigation items in the toolbar and navigation drawer.
     */
    private fun setupNavigationListeners() {
        // Handle toolbar menu items (optional)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.safetyFragment -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }

        // Handle navigation drawer item selection for custom behavior
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settingsFragment -> {
                    // Special handling for settings if needed
                    navController.navigate(R.id.settingsFragment)
                    binding.drawerLayout.closeDrawers()
                    true
                }
                else -> {
                    // Default navigation behavior
                    menuItem.isChecked = true
                    binding.drawerLayout.closeDrawers()
                    navController.navigate(menuItem.itemId)
                    true
                }
            }
        }
    }

    /**
     * This function enables upward navigation within the app's navigation structure.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * This function handles user logout by clearing session data and navigating to the home screen.
     */
    fun handleLogout() {
        // Clear user session data
        val sharedPreferences = getSharedPreferences("SafeNationPrefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Navigate back to home after logout
        navController.navigate(R.id.homeFragment)

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    /**
     * This function allows programmatic navigation to a specific fragment using its ID.
     */
    fun navigateToFragment(fragmentId: Int) {
        navController.navigate(fragmentId)
    }

    /**
     * This function allows navigation to a fragment while passing arguments in a Bundle.
     */
    fun navigateToFragmentWithArgs(fragmentId: Int, args: Bundle) {
        navController.navigate(fragmentId, args)
    }
}