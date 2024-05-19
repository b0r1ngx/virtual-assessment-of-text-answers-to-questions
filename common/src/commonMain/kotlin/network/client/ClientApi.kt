package network.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import Test

/*
 This class executes network requests and deserializes JSON responses
  into entities from the $/$ package.
  The Ktor HttpClient instance initializes and stores the httpClient property.

 This code uses the Ktor ContentNegotiation plugin to deserialize the result of a GET request.
  The plugin processes the request and the response payload as JSON,
  serializing and deserializing them as needed.
 */
class ClientApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    // The network requests will be executed in the HTTP client's thread pool.

    // call it when user first time open tests screen and user pull up (gesture) screen tests screen
    // TODO: add endpoint
    suspend fun getTests(): List<Test> =
        httpClient.get("")
            .body()
}