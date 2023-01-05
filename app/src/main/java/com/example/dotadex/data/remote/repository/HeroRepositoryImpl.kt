package com.example.dotadex.data.remote.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.dotadex.common.ResourceState
import com.example.dotadex.data.local.HeroDao
import com.example.dotadex.data.remote.PostServiceImpl
import com.example.dotadex.data.remote.dto.HeroItemDto
import com.example.dotadex.domain.repository.HeroRepository
import kotlinx.coroutines.flow.*


class HeroRepositoryImpl(
    private val service: PostServiceImpl,
    private val heroDao: HeroDao
    ) : HeroRepository {


    override suspend fun getHeroes(): Flow<ResourceState<List<HeroItemDto>>> = flow {
        val heroListResourceState = service.getHeroes()

        when(heroListResourceState) {
            is ResourceState.Success -> {
                heroListResourceState.data?.let { insertHero(it) }
                emit(heroListResourceState)
            }
            is ResourceState.Failure -> {
                emit(heroListResourceState)
            }
            else -> { Unit }
        }
//        TODO : check for internet connection using work manager to decide data source
    }

    override suspend fun getHeroesOffline(): Flow<List<HeroItemDto>> {
        return heroDao.getHeroes()
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
}