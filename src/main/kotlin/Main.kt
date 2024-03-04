package dev.boringx

import dev.boringx.api.yandex.createPromptRequest
import dev.boringx.datalayer.repository.Repository
import dev.boringx.model.ContextType
import dev.boringx.model.Criterion
import dev.boringx.model.prompt.request.CompletionOptions
import dev.boringx.model.prompt.request.Message
import dev.boringx.model.prompt.request.PromptRequest
import dev.boringx.model.prompt.request.Role
import dev.boringx.utils.createModelUri
import dev.boringx.utils.getEstimationFromPromptResponse

fun main() {
    val repository = Repository()

    val responses = mutableListOf<String>()
    val estimations = mutableListOf<Int>()

    val criteria = Criterion.entries
    val modelUri = createModelUri()
    val questionToAnswerPrompt = repository.getPrompt()
    val completionOptions = CompletionOptions(
        stream = false, temperature = 0.6f, maxTokens = "2000"
    )
    for (criterion in criteria) {
        val prompt = PromptRequest(
            modelUri = modelUri,
            completionOptions = completionOptions,
            messages = listOf(
                Message(
                    role = Role.system.name,
                    text = ContextType.Teacher.description(
                        criterion = criterion
                    )
                ),
                Message(
                    role = Role.user.name,
                    text = questionToAnswerPrompt
                )
            )
        )
        val promptResponse = createPromptRequest(prompt)
        responses.add(promptResponse).also { println(promptResponse) }
        val estimation = getEstimationFromPromptResponse(promptResponse)
        estimations.add(estimation).also { println(estimation) }
        println()
        // TODO: We need to balance how fast we can send requests:
        //  1) just apply Time.sleep
        //  2) if we get error response, check httpCode, if it 429, resend request via timeout 2*DEFAULT_TIME
        Thread.sleep(1000)
    }
    println(responses)
    println(estimations)
    val totalEstimation = estimations.sum() / estimations.size
    println(totalEstimation)
}