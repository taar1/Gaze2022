package net.gazeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import net.gazeapp.data.model.Contact
import net.gazeapp.ui.navigation.BottomNavigation
import net.gazeapp.ui.navigation.NavigationGraph

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreenView()
        }
    }

    @Composable
    fun MainScreenView() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) {
            NavigationGraph(navController = navController)
        }
    }

    private fun testCrash() {
        throw RuntimeException("This is a crash")
    }

    @Composable
    fun CollapsingToolbarInComposeApp(animals: List<Contact>) {
//        CollapsingToolbarInComposeTheme {
//            val context = LocalContext.current
//            Catalog(
//                animals = animals,
//                columns = 2,
//                onPrivacyTipButtonClicked = {
//                    Toast.makeText(context, "Privacy Tip button clicked!", Toast.LENGTH_SHORT).show()
//                },
//                onSettingsButtonClicked = {
//                    Toast.makeText(context, "Settings button clicked!", Toast.LENGTH_SHORT).show()
//                }
//            )
//        }
    }
}