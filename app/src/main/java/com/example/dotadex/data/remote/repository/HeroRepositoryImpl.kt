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
        val heroList = service.getHeroes()
        insertHero(heroList)
        val heroListAsFlow = listOf(heroList).asFlow()
        return heroListAsFlow

//        TODO : check for internet connection using work manager to decide data source
    }


    @WorkerThread
    override suspend fun insertHero(heroItemDto: HeroItemDto) {
        heroDao.insertHero(heroItemDto)
    }

    @WorkerThread
    override suspend fun insertHero(listOfHeroItemDto: List<HeroItemDto>) {
        listOfHeroItemDto.forEach {
            heroDao.insertHero(it)
        }
    }

    override suspend fun getHeroById(heroID: Int): Flow<HeroItemDto> {
        return heroDao.getHeroById(heroID)
    }

}