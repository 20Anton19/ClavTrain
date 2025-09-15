package com.example.clavtrain.navigation

sealed class Route(val path: String) {
    data object Main : Route("Main")
    data object Training : Route("entryLK")
    data object Results : Route("results")
} 