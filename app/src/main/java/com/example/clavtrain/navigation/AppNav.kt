package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clavtrain.ui.AdminModeScreen
import com.example.clavtrain.ui.EntryLKScreen
import com.example.clavtrain.ui.MainScreen
import com.example.clavtrain.ui.RegisterLKScreen

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
                    navController.navigate(Route.EntryLK.path)
                }
            )
        }
        composable(Route.EntryLK.path) {
            EntryLKScreen(
                onLoginClick = {
                    navController.navigate(Route.Main.path) //пока так
                },
                onRegisterClick = {
                    navController.navigate(Route.RegisterLK.path)
                }
            )
        }
        composable(Route.RegisterLK.path) {
            RegisterLKScreen(
                onContinueClick = {
                    navController.navigate(Route.Main.path) //пока так
                }
            )
        }
        composable(Route.AdminMode.path) {
            AdminModeScreen(
                onViewExercises = {
                    navController.navigate(Route.Main.path) //пока так
                },
                onViewDifficultyLevels = {
                    navController.navigate(Route.Main.path) //пока так
                },
                onViewUserStatistics = {
                    navController.navigate(Route.Main.path) //пока так
                },
                onViewStatisticsOfExerciseCompletion = {
                    navController.navigate(Route.Main.path) //пока так
                }
            )
        }
    }
} 