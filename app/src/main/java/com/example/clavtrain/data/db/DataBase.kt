package com.example.clavtrain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [Exercise::class, ExerciseStatistic::class],
    version = 1
)
abstract class DataBase: RoomDatabase() {
    abstract val dao: Dao
}