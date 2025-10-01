package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clavtrain.data.UserRole
import com.example.clavtrain.ui.EntryLKScreen
import com.example.clavtrain.ui.MainScreen
import com.example.clavtrain.ui.RegisterLKScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AuthNav(onRoleDetermined: (UserRole?) -> Unit) {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isSystemBarsVisible = false
    }
    NavHost(navController = navController, startDestination = Route.Main.path) {
        composable(Route.Main.path) {
            MainScreen(
                onLoginClick = {
                    navController.navigate(Route.EntryLK.path)
                },
                onRoleDetermined = onRoleDetermined  // ← Передаем колбэк
            )
        }
        composable(Route.EntryLK.path) {
            EntryLKScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Main.path)
                },
                onRegisterClick = {
                    navController.navigate(Route.RegisterLK.path)
                }
            )
        }
        composable(Route.RegisterLK.path) {
            RegisterLKScreen(
                onContinueClick = {
                    navController.navigate(Route.Main.path)
                }
            )
        }
    }
}
