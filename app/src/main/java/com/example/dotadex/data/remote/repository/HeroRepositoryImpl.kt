package com.example.dotadex.data.remote.repository

import com.example.dotadex.data.remote.PostServiceImpl
import com.example.dotadex.data.remote.dto.HeroItemDto
import com.example.dotadex.domain.repository.HeroRepository


//    TODO Dependency Injection
class HeroRepositoryImpl(private val service: PostServiceImpl) : HeroRepository {

    override suspend fun getHeroes(): List<HeroItemDto> {
        return service.getHeroes()
    }
}