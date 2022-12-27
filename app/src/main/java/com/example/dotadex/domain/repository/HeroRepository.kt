package com.example.dotadex.domain.repository

import com.example.dotadex.data.remote.dto.HeroItemDto
import kotlinx.coroutines.flow.Flow

interface HeroRepository {

    suspend fun getHeroes() : Flow<List<HeroItemDto>>
    suspend fun insertHero(heroItemDto: HeroItemDto) : Unit


}