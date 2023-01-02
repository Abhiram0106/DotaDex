package com.example.dotadex.domain.repository

import com.example.dotadex.data.remote.dto.HeroItemDto
import kotlinx.coroutines.flow.Flow

interface HeroRepository {

    suspend fun getHeroes() : Flow<List<HeroItemDto>>
    suspend fun getHeroesOffline() : Flow<List<HeroItemDto>>
    suspend fun insertHero(heroItemDto: HeroItemDto) : Unit
    suspend fun insertHero(listOfHeroItemDto: List<HeroItemDto>) : Unit
    suspend fun getHeroById(heroID: Int) : Flow<HeroItemDto>
    suspend fun getHeroByName(heroName: String) : Flow<List<HeroItemDto>>

}