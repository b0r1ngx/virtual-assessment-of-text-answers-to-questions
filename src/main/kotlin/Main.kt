package dev.boringx

import dev.boringx.api.yandex.createPromptRequest
import dev.boringx.model.ContextType
import dev.boringx.model.Criteria
import dev.boringx.model.prompt.request.CompletionOptions
import dev.boringx.model.prompt.request.Message
import dev.boringx.model.prompt.request.PromptRequest
import dev.boringx.model.prompt.request.Role
import dev.boringx.datalayer.repository.Repository
import dev.boringx.utils.createModelUri
import dev.boringx.utils.getEstimationFromPromptResponse

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
    val promptResponse = createPromptRequest(prompt)
    println(promptResponse)
    val estimation = getEstimationFromPromptResponse(promptResponse)
}