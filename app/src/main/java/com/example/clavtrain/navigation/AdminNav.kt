package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clavtrain.ui.admin.AdminDifficultyScreen
import com.example.clavtrain.ui.admin.AdminEditDifficultyScreen
import com.example.clavtrain.ui.admin.AdminEditExerciseScreen
import com.example.clavtrain.ui.admin.AdminExercisesScreen
import com.example.clavtrain.ui.admin.AdminModeScreen
import com.example.clavtrain.ui.user.UserExercisesScreen

@Composable
fun AdminNav(onExitApp: () -> Unit) {
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
                },
                onExitApp = {
                    onExitApp()
                }
            )
        }
        composable(Route.AdminExercises.path) {
            AdminExercisesScreen(
                onEditExerciseScreen = {
                    navController.navigate(Route.AdminEditExercise.path)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
            composable(Route.AdminEditExercise.path) {
                AdminEditExerciseScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

        composable(Route.AdminDifficulty.path) {
            AdminDifficultyScreen(
                onEditDifficultyScreen = { levelIndex ->
                    navController.navigate(Route.AdminEditDifficulty.createRoute(levelIndex))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
            composable(
                Route.AdminEditDifficulty.path,
                arguments = listOf(
                    navArgument("levelIndex") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val levelIndex = backStackEntry.arguments?.getInt("levelIndex") ?: 0
                AdminEditDifficultyScreen(
                    levelIndex = levelIndex,  // ← передаем индекс
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Route.UserExercises.path,  // "user_exercises/{levelIndex}"
                arguments = listOf(
                    navArgument("levelIndex") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val levelIndex = backStackEntry.arguments?.getInt("levelIndex") ?: 0

                UserExercisesScreen(
                    levelIndex = levelIndex,  // ← передаем индекс
                    onStartTraining = { exerciseId ->
                        navController.navigate(Route.UserTraining.createRoute(exerciseId))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
    }
}
