package com.example.dotadex.domain.repository

import com.example.dotadex.data.remote.dto.HeroItemDto

interface HeroRepository {

    suspend fun getHeroes() : List<HeroItemDto>

}