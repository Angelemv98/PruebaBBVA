package com.angelemv.android.pruebabbva.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angelemv.android.pruebabbva.dashboard.ui.DashBoard
import com.angelemv.android.pruebabbva.login.ui.LoginScreen
import com.angelemv.android.pruebabbva.splashscreen.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route){
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route = AppScreens.DashBoardScreen.route){
            DashBoard(navController)
        }
    }
}