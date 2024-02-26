package dev.boringx

import dev.boringx.api.yandex.createPromptRequest
import dev.boringx.datalayer.prompt.request.CompletionOptions
import dev.boringx.datalayer.prompt.request.Message
import dev.boringx.datalayer.prompt.request.PromptRequest
import dev.boringx.datalayer.prompt.request.Role
import dev.boringx.utils.createModelUri


fun main() {
    val prompt = PromptRequest(
        modelUri = createModelUri(),
        CompletionOptions(
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
    val responseBody = createPromptRequest(prompt)
    println(responseBody)
}