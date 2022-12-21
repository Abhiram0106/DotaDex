package com.example.dotadex.domain.use_case.get_heroes

import com.example.dotadex.common.ResourceState
import com.example.dotadex.data.remote.dto.toHero
import com.example.dotadex.domain.model.Hero
import com.example.dotadex.domain.repository.HeroRepository
import io.ktor.client.plugins.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// TODO dependency injection
class GetHeroesUseCase (private val repository: HeroRepository) {

    operator fun invoke(): Flow<ResourceState<List<Hero>>> = flow {
        try {
            emit(ResourceState.Loading())
            val heroes = repository.getHeroes().map { it.toHero() } // convert HeroItemDto to Hero coz don't want to deal with extra data
            emit(ResourceState.Success(heroes))
        }catch (e: HttpRequestTimeoutException) {
            emit(ResourceState.Failure(e.localizedMessage ?: "Unexpected HttpRequestTimeoutException error"))
        }catch (e: IOException) {
            emit(ResourceState.Failure(e.localizedMessage ?: "Unexpected IOException error"))
        }
    }
}