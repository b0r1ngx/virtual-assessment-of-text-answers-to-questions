package client.network

import Endpoints
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
    suspend fun getTests(): List<TestModel> {
        return try {
            httpClient.get(Endpoints.test.path).body()
        } catch (e: Exception) {
            println("error executing ClientApi.getTests(), show snackbar?")
            listOf()
        }
    }

    suspend fun createTest(test: TestModel) {
        try {
            httpClient.put(Endpoints.test.path) {
                contentType(ContentType.Application.Json)
                setBody(test)
            }
        } catch (e: Exception) {
            println("error executing ClientApi.createTest(), show snackbar?")
        }
    }


    // TODO: On this endpoint, on server-side, call repository.createUser
    suspend fun registerUser(user: User) {
        try {
            httpClient.put(Endpoints.user.path) {
                contentType(ContentType.Application.Json)
                setBody(user)
            }
        } catch (e: Exception) {
            println("error executing ClientApi.registerUser(), show snackbar?")
        }
    }

}
