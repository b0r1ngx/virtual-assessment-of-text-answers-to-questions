package dev.boringx.api.yandex

import dev.boringx.datalayer.EnvironmentVariables
import dev.boringx.datalayer.prompt.request.PromptRequest
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
    ]
): String {
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(CREATE_PROMPT_URL))
        .POST(
            HttpRequest.BodyPublishers.ofString(
                Json.encodeToString(prompt)
            )
        )
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer $iAmToken")
        .build()

    val response = client.send(
        request, HttpResponse.BodyHandlers.ofString()
    )
    println(response)
    return response.body()
}