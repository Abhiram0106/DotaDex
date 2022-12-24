package com.example.dotadex.data.remote

import com.example.dotadex.data.remote.dto.HeroItemDto
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.Koin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

interface PostService {

    suspend fun getHeroes() : List<HeroItemDto>


}