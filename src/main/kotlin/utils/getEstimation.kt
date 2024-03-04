package dev.boringx.utils

import dev.boringx.api.yandex.sendPromptRequest
import dev.boringx.model.Answer
import dev.boringx.model.ContextType
import dev.boringx.model.Criterion
import dev.boringx.model.Question
import dev.boringx.model.prompt.request.CompletionOptions
import dev.boringx.model.prompt.request.Message
import dev.boringx.model.prompt.request.PromptRequest
import dev.boringx.model.prompt.request.Role

// TODO: Change all print/lns to Timber (from Ktor) or smth like that

fun getEstimation(
    question: Question,
    answer: Answer,
    criteria: List<Criterion> = Criterion.entries,
    modelUri: String = createModelUri(),
    completionOptions: CompletionOptions = CompletionOptions.default
): Float {
    val questionToAnswerPrompt = preparePrompt(question, answer)
    val responses = mutableListOf<String>()
    val estimations = mutableListOf<Int>()
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
        val promptResponse = sendPromptRequest(prompt)
        responses.add(promptResponse).also { println(promptResponse) }
        getEstimationFromPromptResponse(promptResponse).also {
            println(it)
            if (it != -1) {
                estimations.add(it)
            }
        }
        // TODO: Do with this something
        Thread.sleep(1000)
    }
    println(responses)
    println(estimations)
    val totalEstimation = estimations.sum().toFloat() / estimations.size
    println(totalEstimation)
    return totalEstimation
}
