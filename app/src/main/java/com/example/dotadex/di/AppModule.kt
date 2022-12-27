package com.example.dotadex.di

import com.example.dotadex.data.local.HeroDao
import com.example.dotadex.data.local.HeroesRoomDatabase
import com.example.dotadex.data.remote.PostServiceImpl
import com.example.dotadex.data.remote.repository.HeroRepositoryImpl
import com.example.dotadex.domain.repository.HeroRepository
import com.example.dotadex.presentation.hero_list.HeroListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        PostServiceImpl.Companion.create()
    }

    single{
        HeroesRoomDatabase.getDatabase(context = androidContext())
    }

    single<HeroDao> {
        HeroesRoomDatabase.getDatabase(context = androidContext()).heroDao()
    }

    single<HeroRepository> {
        HeroRepositoryImpl(service = get(), heroDao = get())
    }

    viewModel {
        HeroListViewModel(repository = get())
    }

}