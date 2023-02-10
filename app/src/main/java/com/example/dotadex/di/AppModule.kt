package com.example.dotadex.di

import android.app.Application
import com.example.dotadex.data.local.HeroDao
import com.example.dotadex.data.local.HeroesRoomDatabase
import com.example.dotadex.data.remote.PostServiceImpl
import com.example.dotadex.data.remote.repository.HeroRepositoryImpl
import com.example.dotadex.domain.repository.HeroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHeroesService(): PostServiceImpl {
        return PostServiceImpl.create()
    }

//    @Provides
//    @Singleton
//    fun provideHeroDatabase(app: Application): HeroesRoomDatabase {
//        HeroesRoomDatabase.getDatabase(context = app)
//    }

    @Provides
    @Singleton
    fun provideHeroDatabase(app: Application): HeroDao {
        return HeroesRoomDatabase.getDatabase(context = app).heroDao()
    }

    @Provides
    @Singleton
    fun provideHeroRepository(service: PostServiceImpl, heroDao: HeroDao): HeroRepository {
        return HeroRepositoryImpl(service, heroDao)
    }
}