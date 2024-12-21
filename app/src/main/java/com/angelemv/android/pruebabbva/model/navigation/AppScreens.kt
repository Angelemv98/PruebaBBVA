package com.angelemv.android.pruebabbva.model.navigation

sealed class AppScreens (val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object LoginScreen : AppScreens("login_screen")
}