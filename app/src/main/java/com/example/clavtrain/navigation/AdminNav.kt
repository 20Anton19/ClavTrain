package com.example.clavtrain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clavtrain.ui.admin.AdminDifficultyScreen
import com.example.clavtrain.ui.admin.AdminEditDifficultyScreen
import com.example.clavtrain.ui.admin.AdminEditExerciseScreen
import com.example.clavtrain.ui.admin.AdminExerciseStatisticScreen
import com.example.clavtrain.ui.admin.AdminExercisesScreen
import com.example.clavtrain.ui.admin.AdminModeScreen
import com.example.clavtrain.ui.admin.AdminUserStatisticScreen
import com.example.clavtrain.ui.admin.AdminUsersScreen
import com.example.clavtrain.ui.user.UserExercisesScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AdminNav(onExitApp: () -> Unit) {
    val navController = rememberNavController()

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isSystemBarsVisible = false
    }
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
                onEditExerciseScreen = { exerciseId ->
                    navController.navigate(Route.AdminEditExercise.createRoute(exerciseId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
            composable(
                Route.AdminEditExercise.path,
                arguments = listOf(
                    navArgument("exerciseId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: 0
                AdminEditExerciseScreen(
                    exerciseId = exerciseId,
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

        composable(Route.AdminUsers.path) {
            AdminUsersScreen(
                onViewAdminUserStatistic = { userId ->
                    navController.navigate(Route.AdminUserStatistic.createRoute(userId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
            composable(
                Route.AdminUserStatistic.path,
                arguments = listOf(
                    navArgument("userId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: ""
                AdminUserStatisticScreen(
                    userId = userId,
                    onViewAdminExerciseStatistic = { exerciseId ->
                        navController.navigate(Route.AdminExerciseStatistic.createRoute(exerciseId))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
                composable(
                    Route.AdminExerciseStatistic.path,
                    arguments = listOf(
                        navArgument("exerciseId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: 0
                    AdminExerciseStatisticScreen(
                        exerciseId = exerciseId,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
    }
}
