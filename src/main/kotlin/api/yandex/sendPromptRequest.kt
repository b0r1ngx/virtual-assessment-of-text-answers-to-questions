package dev.boringx.api.yandex

import dev.boringx.model.EnvironmentVariables
import dev.boringx.model.prompt.request.PromptRequest
import dev.boringx.dotenv
import dev.boringx.utils.encodeToString
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/* Realisation of https://cloud.yandex.ru/ru/docs/yandexgpt/operations/create-prompt

   TODO: Create Data Source (provider) that takes responsibilities of:
     1. Balance how fast we can send requests:
     1.1 just apply Time.sleep
     1.2 if we get error response, check httpCode, if it 429, resend request via timeout 2*DEFAULT_TIME
*/

private const val CREATE_PROMPT_URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"

private val CLIENT = HttpClient.newBuilder().build()
private val CREATE_PROMPT_REQUEST = HttpRequest.newBuilder()
    .header("Content-Type", "application/json")
    .uri(URI.create(CREATE_PROMPT_URL))

fun sendPromptRequest(
    prompt: PromptRequest,
    iAmToken: String = dotenv[EnvironmentVariables.BEARER_TOKEN.name]
): String {
    val request = CREATE_PROMPT_REQUEST
        .header("Authorization", "Bearer $iAmToken")
        .POST(HttpRequest.BodyPublishers.ofString(prompt.encodeToString()))
        .build()

    return CLIENT.send(request).body()
}

private fun HttpClient.send(request: HttpRequest) =
    CLIENT.send(request, HttpResponse.BodyHandlers.ofString())
