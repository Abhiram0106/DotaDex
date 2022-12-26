package com.example.dotadex.di

import com.example.dotadex.data.remote.PostServiceImpl
import com.example.dotadex.data.remote.repository.HeroRepositoryImpl
import com.example.dotadex.domain.repository.HeroRepository
import com.example.dotadex.presentation.hero_list.HeroListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        PostServiceImpl.Companion.create()
    }

    single<HeroRepository> {
        HeroRepositoryImpl(get())
    }

    viewModel {
        HeroListViewModel(get())
    }

}