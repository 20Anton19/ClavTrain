package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clavtrain.ui.admin.AdminModeScreen

@Composable
fun AdminNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.AdminMain.path) {
        composable(Route.AdminMain.path) {
            AdminModeScreen(
                onViewExercises = {
                    navController.navigate(Route.AdminExercises.path)
                },
                onViewDifficultyLevels = {
                    navController.navigate(Route.AdminDifficulty.path)
                },
                onViewUserStatistics = {
                    navController.navigate(Route.AdminUsers.path)
                },
                onViewStatisticsOfExerciseCompletion = {
                    navController.navigate(Route.AdminStats.path)
                }
            )
        }
    }
}
