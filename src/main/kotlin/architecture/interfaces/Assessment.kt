package dev.boringx.architecture.interfaces

import dev.boringx.model.Answer
import dev.boringx.model.Criterion
import dev.boringx.model.Question
import dev.boringx.model.prompt.request.CompletionOptions

interface Assessment {
    val promptRequestFormatter: PromptRequestFormatter
    val promptResponseParser: PromptResponseParser

    val criteria: List<Criterion> // Criterion.entries
    val modelUri: String // createModelUri()
    val completionOptions: CompletionOptions // CompletionOptions.default

    fun assess(question: Question, answer: Answer): Float
}