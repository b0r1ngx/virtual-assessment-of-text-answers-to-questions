package dev.boringx

import dev.boringx.api.yandex.createPromptRequest
import dev.boringx.datalayer.ContextType
import dev.boringx.datalayer.Criteria
import dev.boringx.datalayer.prompt.request.CompletionOptions
import dev.boringx.datalayer.prompt.request.Message
import dev.boringx.datalayer.prompt.request.PromptRequest
import dev.boringx.datalayer.prompt.request.Role
import dev.boringx.datalayer.repository.Repository
import dev.boringx.utils.createModelUri

fun main() {
    val repository = Repository()
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
                text = ContextType.Teacher.description(
                    criteria = Criteria.Correctness
                )
            ),
            Message(
                role = Role.user.name,
                text = repository.getPrompt()
            )
        )
    )
    val responseBody = createPromptRequest(prompt)
    println(responseBody)
}