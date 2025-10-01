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
    object UserTraining : Route("user_training/{exerciseId}") {  // ← добавляем параметр
        fun createRoute(exerciseId: Int) = "user_training/$exerciseId"
    }
    data object UserExerciseStatistic : Route("user_exercise_statistic")

    data object ChangeUserData : Route("change_user_data")
    data object ChangePassword : Route("change_password")

    data object UserStatistic : Route("user_statistic")
    
    // Admin routes
    data object AdminMain : Route("admin_main")
    data object AdminExercises : Route("admin_exercises")
    data object AdminDifficulty : Route("admin_difficulty")
    data object AdminUsers : Route("admin_users")
    data object AdminStats : Route("admin_stats")



    data object AdminEditExercise : Route("admin_edit_exercise")

    data object AdminEditDifficulty : Route("admin_edit_difficulty/{levelIndex}"){  // ← добавляем параметр
        fun createRoute(levelIndex: Int) = "admin_edit_difficulty/$levelIndex"
    }










} 