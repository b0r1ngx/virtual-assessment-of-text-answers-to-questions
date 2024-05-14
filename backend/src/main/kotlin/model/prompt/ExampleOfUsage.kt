package model.prompt

import dev.boringx.model.prompt.request.CompletionOptions
import dev.boringx.model.prompt.request.Message
import dev.boringx.model.prompt.request.PromptRequest
import dev.boringx.model.prompt.request.Role
import dev.boringx.utils.decodeFromStringSafety
import dev.boringx.utils.encodeToString

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
    val objectFromJson = createPrompt.encodeToString()
    println(objectFromJson)
    val deserializingToObject = objectFromJson.decodeFromStringSafety<PromptRequest>()
    println(deserializingToObject)
}