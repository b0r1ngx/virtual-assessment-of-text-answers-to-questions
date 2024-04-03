package dev.boringx.architecture.realisations

import dev.boringx.architecture.interfaces.Assessment
import dev.boringx.architecture.interfaces.PromptRequestFormatter
import dev.boringx.architecture.interfaces.PromptResponseParser
import dev.boringx.model.Answer
import dev.boringx.model.Criterion
import dev.boringx.model.Question
import dev.boringx.model.prompt.request.CompletionOptions

class Assessment(
    override val promptRequestFormatter: PromptRequestFormatter,
    override val promptResponseParser: PromptResponseParser,
    override val criteria: List<Criterion>,
    override val modelUri: String,
    override val completionOptions: CompletionOptions
) : Assessment {
    override fun assess(question: Question, answer: Answer): Float {
        TODO("Not yet implemented")
    }

}