package com.example.mywishlist.presentation.routes

sealed class Screen(val route: String) {
    data object HomeScreen: Screen(route = "home_screen")
    data object AddScreen: Screen(route = "add_screen")
}