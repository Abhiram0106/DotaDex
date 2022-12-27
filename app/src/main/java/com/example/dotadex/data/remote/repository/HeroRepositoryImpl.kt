package com.example.dotadex.data.remote.repository

import androidx.annotation.WorkerThread
import com.example.dotadex.data.local.HeroDao
import com.example.dotadex.data.remote.PostServiceImpl
import com.example.dotadex.data.remote.dto.HeroItemDto
import com.example.dotadex.domain.repository.HeroRepository
import kotlinx.coroutines.flow.*


class HeroRepositoryImpl(
    private val service: PostServiceImpl,
    private val heroDao: HeroDao
    ) : HeroRepository {


    override suspend fun getHeroes(): Flow<List<HeroItemDto>> {
        val wtf = service.getHeroes()
        val what = listOf(wtf).asFlow()
        return what

//        TODO : check for internet connection using work manager to decide data source
    }


    @WorkerThread
    override suspend fun insertHero(heroItemDto: HeroItemDto) {
        heroDao.insertHero(heroItemDto)
    }

}