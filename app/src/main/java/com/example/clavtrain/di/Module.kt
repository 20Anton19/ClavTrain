package com.example.clavtrain.di


import android.app.Application
import androidx.room.Room
import com.example.clavtrain.data.db.DataBase
import com.example.clavtrain.data.db.DataBaseViewModel
import com.example.clavtrain.ui.EntryLKViewModel
import com.example.clavtrain.ui.MainViewModel
import com.example.clavtrain.ui.RegisterLKViewModel
import com.example.clavtrain.ui.user.UserTrainingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

import org.koin.android.ext.koin.androidApplication


fun createDatabase(application: Application): DataBase {
    return Room.databaseBuilder(
        application,
        DataBase::class.java,
        "app_database"
    ).build()
}

val mainModule = module {
    single { createDatabase(get()) }
    single<DataBaseViewModel> { DataBaseViewModel(get()) } // ← ОДНА на всё приложение
    viewModelOf(::MainViewModel)
    viewModelOf(::EntryLKViewModel)
    viewModelOf(::RegisterLKViewModel)
    viewModelOf(::UserTrainingViewModel)
}