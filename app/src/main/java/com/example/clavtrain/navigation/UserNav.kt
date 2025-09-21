package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clavtrain.ui.MainScreen
import com.example.clavtrain.ui.RegisterLKScreen

@Composable
fun UserNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.UserMain.path) {
        /*
        composable(Route.UserTraining.path) {
            // TODO: UserTrainingScreen
            MainScreen(
                onStartTraining = {
                    navController.navigate(Route.UserMain.path)
                }
            )
        }
        composable(Route.UserResults.path) {
            // TODO: UserResultsScreen
            MainScreen(
                onStartTraining = {
                    navController.navigate(Route.UserMain.path)
                }
            )
        }
         */
    }
}
