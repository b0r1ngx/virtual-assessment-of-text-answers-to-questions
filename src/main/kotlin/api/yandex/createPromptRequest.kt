package dev.boringx.api.yandex

import dev.boringx.model.EnvironmentVariables
import dev.boringx.model.prompt.request.PromptRequest
import dev.boringx.dotenv
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private const val CREATE_PROMPT_URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"

// https://cloud.yandex.ru/ru/docs/yandexgpt/operations/create-prompt
fun createPromptRequest(
    prompt: PromptRequest,
    iAmToken: String = dotenv[
        EnvironmentVariables.BEARER_TOKEN.name
    ],
    folderId: String = dotenv[
        EnvironmentVariables.FOLDER_ID.name
    ]
): String {
    val body = Json.encodeToString(prompt)
    println("Request with body of: $body")
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(CREATE_PROMPT_URL))
        .POST(
            HttpRequest.BodyPublishers.ofString(
                body
            )
        )
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer $iAmToken")
//        .header("x-folder-id", folderId)
        .build()
    println("Created request to send: $request")
    val response = client.send(
        request, HttpResponse.BodyHandlers.ofString()
    )
    println("Response: $response")
    return response.body()
}