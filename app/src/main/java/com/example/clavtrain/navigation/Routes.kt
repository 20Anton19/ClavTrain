package com.example.clavtrain.navigation

sealed class Route(val path: String) {

    data object Main : Route("main")
    // Auth routes
    data object EntryLK : Route("entryLK")
    data object RegisterLK : Route("registerLK")
    
    // User routes
    data object UserMenu : Route("user_menu")
    data object UserDifficulty : Route("user_difficulty")
    data object UserLK : Route("user_lk")
    data object Info : Route("info")
    data object AboutDevelopers : Route("about_developers")

    object UserExercises : Route("user_exercises/{levelIndex}") {  // ← добавляем параметр
        fun createRoute(levelIndex: Int) = "user_exercises/$levelIndex"
    }
    data object UserTraining : Route("user_training")
    data object UserExerciseStatistic : Route("user_exercise_statistic")
    
    // Admin routes
    data object AdminMain : Route("admin_main")
    data object AdminMode : Route("admin_mode")
    data object AdminExercises : Route("admin_exercises")
    data object AdminDifficulty : Route("admin_difficulty")
    data object AdminUsers : Route("admin_users")
    data object AdminStats : Route("admin_stats")
} 