package com.example.dotadex.data.remote

import android.util.Log
import com.example.dotadex.common.Constants
import com.example.dotadex.common.ResourceState
import com.example.dotadex.data.remote.dto.HeroItemDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class PostServiceImpl(
    private val client: HttpClient
) {
    suspend fun getHeroes(): ResourceState<List<HeroItemDto>> {
        try {

            val response = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = Constants.BASE_URL
                    appendPathSegments(Constants.API_PATH, Constants.HERO_STATS)
                }
            }

            return when (response.status.value) {
                in 200..299 -> ResourceState.Success(response.body())
                else -> ResourceState.Failure("${response.status.value}:${response.status.description}")
            }
        } catch (c: Throwable) {
            Log.e(Constants.TAG, "getHeroes: $c")
        }
        return ResourceState.Failure("No internet connection")
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
}