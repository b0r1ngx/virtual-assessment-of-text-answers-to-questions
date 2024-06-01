package api.yandex

import dotenv
import model.EnvironmentVariables
import model.prompt.request.PromptRequest
import utils.encodeToString
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/* Realisation of https://cloud.yandex.ru/ru/docs/yandexgpt/operations/create-prompt

   TODO: Create Data Source (provider) that takes responsibilities of:
     1. Balance how fast we can send requests:
     1.1 just apply Time.sleep
     1.2 if we get error response, check httpCode, if it 429, resend request via timeout 2*DEFAULT_TIME

   TODO:
     2.  Try to place client here: to get what pros??
     3. When its hosted on server use API key instead of iAmToken, that only lives 24h?
     4. Check for concepts of usage YaGPT: https://cloud.yandex.com/ru/docs/api-design-guide/
*/

private const val CREATE_PROMPT_URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"

fun sendPromptRequest(
    prompt: PromptRequest,
    iAmToken: String = dotenv[EnvironmentVariables.BEARER_TOKEN.name],
    client: HttpClient = HttpClient.newBuilder().build(),
    uri: URI = URI.create(CREATE_PROMPT_URL)
): String {
    val request = HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer $iAmToken")
        .POST(HttpRequest.BodyPublishers.ofString(prompt.encodeToString()))
        .uri(uri)
        .build()

    return client.send(request).body()
}

private fun HttpClient.send(request: HttpRequest) =
    this.send(request, HttpResponse.BodyHandlers.ofString())
