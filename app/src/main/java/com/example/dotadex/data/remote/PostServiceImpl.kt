package com.example.dotadex.data.remote

import com.example.dotadex.common.Constants
import com.example.dotadex.data.remote.dto.HeroItemDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

class PostServiceImpl(
    private val client: HttpClient
) {
    suspend fun getHeroes() : List<HeroItemDto> {
        return client.get(urlString = Constants.HERO_STATS).body()
    }

    companion object {
        fun create(): PostServiceImpl {
            return PostServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        json()
                    }
                }
            )

        }
    }

//    companion object {
//        fun create(): PostService {
//            return PostServiceImpl(
//                client = HttpClient(Android) {
//                    install(Logging) {
//                        level = LogLevel.ALL
//                    }
//                    install(ContentNegotiation) {
//                        json()
//                    }
//                }
//            )
//        }
//    }
}