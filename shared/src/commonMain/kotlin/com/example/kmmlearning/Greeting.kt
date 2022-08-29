package com.example.kmmlearning

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Greeting {
    private val httpClient = HttpClient {
        install(ContentNegotiation){
            json(Json {
                prettyPrint=true
                isLenient=true
                ignoreUnknownKeys=true
            })
        }
    }
    @Throws(Exception::class)
    suspend fun greeting(): String {
        val rokets:List<RocketLaunch> = httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val lastSuccessLaunch = rokets.last { it.launchSuccess==true }

        return "Guess what it is! > ${Platform().platform.reversed()}!" +
                "\nThere are only ${daysUntilYear()} left until New Year! 🎅🏼 " +
                "\nThe last successful launch was ${lastSuccessLaunch.launchDateUTC} 🚀"
    }
}