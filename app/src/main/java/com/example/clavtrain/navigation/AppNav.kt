package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clavtrain.ui.EntryLKScreen
import com.example.clavtrain.ui.MainScreen
import com.example.clavtrain.ui.TrainingScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Main.path
    ) {
        composable(Route.Main.path) {
            MainScreen(
                onStartTraining = {
                    navController.navigate(Route.Training.path)
                }
            )
        }
        composable(Route.Training.path) {
            TrainingScreen(
                onFinish = {
                    navController.navigate(Route.Results.path)
                }
            )
        }
        composable(Route.Results.path) {
            EntryLKScreen(
                onBackToStart = {
                    navController.popBackStack(route = Route.Main.path, inclusive = false)
                }
            )
        }
    }
} 