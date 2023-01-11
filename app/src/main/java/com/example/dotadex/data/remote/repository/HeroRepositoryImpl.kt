package com.example.dotadex.data.remote.repository

import androidx.annotation.WorkerThread
import com.example.dotadex.common.ResourceState
import com.example.dotadex.data.local.HeroDao
import com.example.dotadex.data.remote.PostServiceImpl
import com.example.dotadex.data.remote.dto.HeroItemDto
import com.example.dotadex.domain.repository.HeroRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class HeroRepositoryImpl(
    private val service: PostServiceImpl,
    private val heroDao: HeroDao
    ) : HeroRepository {


    override suspend fun getHeroes(): Flow<ResourceState<List<HeroItemDto>>> = flow {
        val heroListResourceState = service.getHeroes()

        when(heroListResourceState) {
            is ResourceState.Success -> {
                heroListResourceState.data?.let { insertHero(it) }
                emit(getHeroesOffline())
            }
            is ResourceState.Failure -> {
                emit(heroListResourceState)
                emit(getHeroesOffline())
            }
            else -> {  }
        }
    }

    override suspend fun getHeroesOffline(): ResourceState<List<HeroItemDto>> {
        val job = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            ResourceState.Success(heroDao.getHeroes())
        }
        return job
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


    override suspend fun getHeroByName(heroName: String): Flow<List<HeroItemDto>> {
        return heroDao.getHeroByName(heroName)
    }

    override suspend fun filterListByAttribute(filter: List<String>): Flow<List<HeroItemDto>> {
        return heroDao.filterByAttribute(filter)
    }
}