package net.gazeapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.gazeapp.utilities.TrackingUtils

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)

//        testCrash()
        bottomBarNavigation()
        init()

        lifecycleScope.launch {
            aaid()
        }

    }

    /**
     * Navigation Component
     */
    private fun bottomBarNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNav, navController)

        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNav.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(
                item,
                Navigation.findNavController(this, R.id.navHostFragment)
            )
        }
    }

    private fun testCrash() {
        throw RuntimeException("This is a crash")
    }

    private fun init() {
        TrackingUtils.getInstance(this)
    }

    private suspend fun aaid() {
        withContext(Dispatchers.IO) {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
            val userId: String? = adInfo.id

            Log.d(TAG, "> AAAID: $userId")
        }
    }
}