package net.gazeapp.ui.navigation

import net.gazeapp.R


sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {

    object Recent : BottomNavItem("Recent", R.drawable.ic_recent, "recent")
    object All : BottomNavItem("All", R.drawable.ic_contacts, "all")
    object Me : BottomNavItem("Me", R.drawable.ic_me, "me")
    object Settings : BottomNavItem("Settings", R.drawable.ic_settings, "settings")
}