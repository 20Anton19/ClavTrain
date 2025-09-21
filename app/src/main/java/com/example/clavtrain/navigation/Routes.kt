package com.example.clavtrain.navigation

sealed class Route(val path: String) {

    data object Main : Route("main")
    // Auth routes
    data object EntryLK : Route("entryLK")
    data object RegisterLK : Route("registerLK")
    
    // User routes
    data object UserMain : Route("user_main")
    data object UserTraining : Route("user_training")
    data object UserResults : Route("user_results")
    
    // Admin routes
    data object AdminMain : Route("admin_main")
    data object AdminMode : Route("admin_mode")
    data object AdminExercises : Route("admin_exercises")
    data object AdminDifficulty : Route("admin_difficulty")
    data object AdminUsers : Route("admin_users")
    data object AdminStats : Route("admin_stats")
} 