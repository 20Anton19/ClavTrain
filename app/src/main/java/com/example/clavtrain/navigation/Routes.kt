package com.example.clavtrain.navigation

sealed class Route(val path: String) {
    data object Main : Route("main")
    data object EntryLK : Route("entryLK")
    data object Results : Route("results")
    data object RegisterLK : Route("registerLK")
    data object AdminMode : Route("adminMode")
} 