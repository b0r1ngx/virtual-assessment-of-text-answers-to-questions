package client.network

import SERVER_URL
import TestModel
import User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/*
 This class executes network requests and deserializes JSON responses
  into entities from the $/$ package.
  The Ktor HttpClient instance initializes and stores the httpClient property.

 This code uses the Ktor ContentNegotiation plugin to deserialize the result of a GET request.
  The plugin processes the request and the response payload as JSON,
  serializing and deserializing them as needed.
 */
class ClientApi {
    // The network requests will be executed in the HTTP client's thread pool.
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        install(DefaultRequest) {
            url(SERVER_URL)
        }
    }

    // call it when user first time open tests screen or user pull up (gesture) screen tests screen
    // TODO: Declare all endpoints at enum class in common module
    suspend fun getTests(): List<TestModel> =
        httpClient.get("/test").body()

    suspend fun createTest(test: TestModel) {
        httpClient.put("/test") {
            contentType(ContentType.Application.Json)
            setBody(test)
        }
    }


    // TODO: On this endpoint, on server-side, call repository.createUser
    suspend fun registerUser(user: User) =
        httpClient.put("/user") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
}
