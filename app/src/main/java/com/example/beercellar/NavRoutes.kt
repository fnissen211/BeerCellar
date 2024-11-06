package com.example.beercellar

sealed class NavRoutes(val route: String) {
    data object CreateAcc : NavRoutes("createAccount")
    data object Login : NavRoutes("login")
    data object Home : NavRoutes("home")
}