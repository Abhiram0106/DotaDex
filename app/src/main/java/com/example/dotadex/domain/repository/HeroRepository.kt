package com.example.dotadex.domain.repository

import com.example.dotadex.common.ResourceState
import com.example.dotadex.data.remote.dto.HeroItemDto
import kotlinx.coroutines.flow.Flow

interface HeroRepository {

    suspend fun getHeroes(): Flow<ResourceState<List<HeroItemDto>>>
    suspend fun getHeroesOffline(): ResourceState<List<HeroItemDto>>
    suspend fun insertHero(heroItemDto: HeroItemDto)
    suspend fun insertHero(listOfHeroItemDto: List<HeroItemDto>)
    suspend fun getHeroById(heroID: Int): Flow<HeroItemDto>
    suspend fun getHeroByName(heroName: String): Flow<List<HeroItemDto>>
    suspend fun filterListByAttribute(filter: List<String>): Flow<List<HeroItemDto>>

}