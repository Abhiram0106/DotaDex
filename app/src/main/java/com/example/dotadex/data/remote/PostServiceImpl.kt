package com.example.dotadex.data.remote

import com.example.dotadex.common.Constants
import com.example.dotadex.data.remote.dto.HeroItemDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PostServiceImpl(
    private val client: HttpClient
) : PostService {
    override suspend fun getHeroes() : List<HeroItemDto> {
        return client.get(urlString = Constants.HERO_STATS).body()
    }
}