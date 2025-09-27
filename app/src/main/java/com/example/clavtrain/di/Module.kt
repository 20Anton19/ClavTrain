package com.example.clavtrain.di


import com.example.clavtrain.ui.EntryLKScreen
import com.example.clavtrain.ui.EntryLKViewModel
import com.example.clavtrain.ui.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::EntryLKViewModel)
}