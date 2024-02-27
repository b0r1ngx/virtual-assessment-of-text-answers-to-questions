package dev.boringx.datalayer.prompt

import dev.boringx.datalayer.prompt.request.CompletionOptions
import dev.boringx.datalayer.prompt.request.Message
import dev.boringx.datalayer.prompt.request.PromptRequest
import dev.boringx.datalayer.prompt.request.Role
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main() {
    val createPrompt = PromptRequest(
        modelUri = "gpt://b1gjcmmah16shmb9g8hq/yandexgpt-lite",
        completionOptions = CompletionOptions(
            stream = false,
            temperature = 0.6f,
            maxTokens = "2000"
        ),
        messages = listOf(
            Message(
                role = Role.system.name,
                text = "Найди ошибки в тексте и исправь их"
            ),
            Message(
                role = Role.user.name,
                text = "Ламинат подойдет для укладке на кухне или в детской комнате – он не боиться влаги и механических повреждений благодаря защитному слою из облицованных меламиновых пленок толщиной 0,2 мм и обработанным воском замкам."
            )
        )
    )
    val objectFromJson = Json.encodeToString(createPrompt)
    println(objectFromJson)
    val deserializingToObject = Json.decodeFromString<PromptRequest>(objectFromJson)
    println(deserializingToObject)
}