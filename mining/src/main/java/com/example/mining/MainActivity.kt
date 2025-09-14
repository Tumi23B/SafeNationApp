package com.example.safenationapp

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.safenation.core.BaseActivity
import com.safenation.mining.R
import com.safenation.mining.databinding.ActivityMainBinding
import org.osmdroid.config.Configuration

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // REMOVED duplicate setContentView()
        setupNavigation()

        // SETUP ACTIONBAR FIRST - This is the critical fix
        //setupActionBar(binding.toolbar)
// Initialize osmdroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        Configuration.getInstance().userAgentValue = packageName
    }

    private fun setupNavigation() {
        // Get NavHostFragment and NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup AppBarConfiguration with top-level destinations
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
        //setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbar?.setupWithNavController(navController, appBarConfiguration)
        // Setup NavigationView with NavController
        binding.navigationView.setupWithNavController(navController)

        // Setup BottomNavigationView with NavController
        binding.bottomNavigation.setupWithNavController(navController)

        // Handle navigation item selection for custom behavior
        setupNavigationListeners()
    }

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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Method to handle logout from SettingsFragment
    fun handleLogout() {
        // Clear user session data
        val sharedPreferences = getSharedPreferences("SafeNationPrefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Navigate back to home after logout
        navController.navigate(R.id.homeFragment)

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    // Method to open specific fragment programmatically
    fun navigateToFragment(fragmentId: Int) {
        navController.navigate(fragmentId)
    }

    // Method to open specific fragment with arguments
    fun navigateToFragmentWithArgs(fragmentId: Int, args: Bundle) {
        navController.navigate(fragmentId, args)
    }

}