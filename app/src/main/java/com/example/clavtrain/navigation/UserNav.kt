package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clavtrain.ui.user.AboutDevelopersScreen
import com.example.clavtrain.ui.user.InfoScreen
import com.example.clavtrain.ui.user.UserDifficultyScreen
import com.example.clavtrain.ui.user.UserExerciseStatisticScreen
import com.example.clavtrain.ui.user.UserExercisesScreen
import com.example.clavtrain.ui.user.UserLKScreen
import com.example.clavtrain.ui.user.UserMenuScreen
import com.example.clavtrain.ui.user.UserTrainingScreen

@Composable
fun UserNav(onExitApp: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.UserMenu.path) {
        composable(Route.UserMenu.path) {
            UserMenuScreen(
                onViewDifficulty = {
                    navController.navigate(Route.UserDifficulty.path)
                },
                onViewUserLK = {
                    navController.navigate(Route.UserLK.path)
                },
                onViewInfo = {
                    navController.navigate(Route.Info.path)
                },
                onViewAboutDevelopers = {
                    navController.navigate(Route.AboutDevelopers.path)
                },
                onExitApp = {
                    onExitApp()
                }
            )
        }
            composable(Route.UserDifficulty.path) {
                UserDifficultyScreen(
                    onViewExcercises = { levelIndex ->
                        navController.navigate(Route.UserExercises.createRoute(levelIndex))
                    },
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
            composable(
                route = Route.UserTraining.path,
                arguments = listOf(navArgument("exerciseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: 0
                UserTrainingScreen(
                    exerciseId = exerciseId,
                    onViewStatistics = {
                        navController.navigate(Route.UserExerciseStatistic.path)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Route.UserExerciseStatistic.path) {
                UserExerciseStatisticScreen(
                    onViewMenu = {
                        navController.navigate(Route.UserMenu.path) {
                            popUpTo(Route.UserMenu.path) { inclusive = true }
                        }
                    }
                )
            }
        composable(Route.UserLK.path) {
            UserLKScreen()
        }
        composable(Route.Info.path) {
            InfoScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Route.AboutDevelopers.path) {
            AboutDevelopersScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
