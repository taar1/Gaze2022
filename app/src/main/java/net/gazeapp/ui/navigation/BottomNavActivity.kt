package net.gazeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Recent.screen_route) {
        composable(BottomNavItem.Recent.screen_route) {
            RecentScreen()
        }
        composable(BottomNavItem.All.screen_route) {
            AllScreen()
        }
        composable(BottomNavItem.Me.screen_route) {
            MeScreen()
        }
        composable(BottomNavItem.Settings.screen_route) {
            SettingsScreen()
        }
    }


    // TODO hier weitermachen:
    // TODO hier weitermachen:
    // TODO hier weitermachen:
    // TODO hier weitermachen:
    // TODO hier weitermachen:
    // https://medium.com/geekculture/bottom-navigation-in-jetpack-compose-android-9cd232a8b16
    //
    // bei:
    // "Now you need to create new function to define bottom navigation, its item, handling bottom navigation backstack and defining start destination."
}