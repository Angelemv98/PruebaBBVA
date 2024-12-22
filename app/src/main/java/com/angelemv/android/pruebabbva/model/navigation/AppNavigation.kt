package com.angelemv.android.pruebabbva.model.navigation

import UserPreferencesManager
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angelemv.android.pruebabbva.viewmodel.ViewModel
import com.angelemv.android.pruebabbva.views.DashBoard
import com.angelemv.android.pruebabbva.views.LoginScreen
import com.angelemv.android.pruebabbva.views.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route){
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController, UserPreferencesManager(navController.context))
        }
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController, ViewModel(navController.context))
        }
        composable(route = AppScreens.DashBoardScreen.route){
            DashBoard(navController, ViewModel(navController.context))
        }

    }

}